package eternal.hoge.spring.boot.example.simple.utils.httpconnection;

import eternal.hoge.spring.boot.example.simple.utils.LogServiceUtil;
import lombok.Cleanup;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.DocumentValueConstant.FOUR_KB;
import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.ResponseKey.STATUS_CODE;
import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.StatusConstantNumber.GATEWAY_TIMEOUT;
import static eternal.hoge.spring.boot.example.simple.utils.CoreConstantUtil.ValueConstant.*;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
public final class GenericUrlConnectionBuilder {

    private HttpURLConnection httpURLConnection;
    @Getter
    private Object data;
    @Getter
    private Exception exception;
    @Getter
    private Integer statusCode = GATEWAY_TIMEOUT;
    private List<String> requestPropertyKey;
    private List<String> requestPropertyValue;

    GenericUrlConnectionBuilder() {
        requestPropertyKey = new ArrayList<>();
        requestPropertyValue = new ArrayList<>();
    }

    static GenericUrlConnectionBuilder getInstance() {
        return new GenericUrlConnectionBuilder();
    }

    private static void getSslCertified() throws NoSuchAlgorithmException, KeyManagementException {
        log.trace(INSIDE_METHOD, "getSslCertified");
        val trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                isCertsAuthType(certs, authType);
            }

            private void isCertsAuthType(X509Certificate[] certs, String authType) throws CertificateException {
                boolean certificiateByPass = false;
                if (certs != null || authType != null)
                    certificiateByPass = true;
                if (!certificiateByPass)
                    throw new CertificateException("AuthType or Certs is null " + certs + " " + authType);
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
                isCertsAuthType(certs, authType);
            }
        }
        };

        // Install the all-trusting trust manager
        val sc = SSLContext.getInstance("TLSv1.2");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = (hostname, session) -> hostname.equalsIgnoreCase(session.getPeerHost());

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    public GenericUrlConnectionBuilder setUrl(String url) {
        log.trace(INSIDE_METHOD, "setUrl");
        try {
            log.info("Url for sending http request", kv("url", url));
            val mUrl = new URL(url);
            this.httpURLConnection = (HttpURLConnection) mUrl.openConnection();
        } catch (Exception e) {
            this.exception = e;
            log.error("Exception occurred while opening url connection", kv(EXCEPTION_LABEL_TAG, e.getMessage()), kv(SET_URL, url), e);
        }
        return this;
    }

    public GenericUrlConnectionBuilder getCertified() {
        log.trace(INSIDE_METHOD, "getCertified");
        try {
            getSslCertified();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            log.error("Exception occurred while getting ssl certified", kv(EXCEPTION_LABEL_TAG, e.getMessage()), e);
            LogServiceUtil.errorMetrics(e);

        }
        return this;
    }

    public GenericUrlConnectionBuilder setRequestProperty(String key, String value) {
        log.trace(INSIDE_METHOD, "setRequestProperty");
        this.requestPropertyKey.add(key);
        this.requestPropertyValue.add(value);
        return this;
    }

    public GenericUrlConnectionBuilder sendRequest(String requestKey) {
        log.trace(INSIDE_METHOD, "sendRequest");
        log.info("@sendRequest",kv("RequestKey",requestKey));
        try {
            if (!this.httpURLConnection.getDoOutput()) {
                setRequestPropertyKeyValue();
            }
            this.statusCode = httpURLConnection.getResponseCode();

            log.debug("Getting status code from http request", LogServiceUtil.logKV().add(STATUS_CODE, this.statusCode).build());

            getResponseData();
        } catch (Exception ex) {
            this.exception = ex;
            log.error("Exception occurred while getting response from server", kv(EXCEPTION_LABEL_TAG, ex.getMessage()), ex);
            LogServiceUtil.errorMetrics(ex);
        }
        return this;
    }

    public GenericUrlConnectionBuilder setRequestMethod(String requestMethod) {
        log.trace(INSIDE_METHOD, "setRequestMethod");
        try {
            this.httpURLConnection.setRequestMethod(requestMethod);
            log.info("Request method for http connection", kv("requestMethod", requestMethod));
        } catch (ProtocolException e) {
            this.exception = e;
            log.error("Exception occurred while sending request to server", kv(EXCEPTION_LABEL_TAG, e.getMessage()), e);
            LogServiceUtil.errorMetrics(e);


        }
        return this;
    }

    private void setRequestPropertyKeyValue() {

        if (!requestPropertyKey.isEmpty()) {
            for (int i = 0; i < requestPropertyKey.size(); i++) {
                this.httpURLConnection.setRequestProperty(requestPropertyKey.get(i), requestPropertyValue.get(i));
            }
        }
    }

    public GenericUrlConnectionBuilder putFileToServer(String[] uploadFile, String fileHolder) {
        log.trace(INSIDE_METHOD, "putFileToServer");
        this.httpURLConnection.setDoOutput(true);
        val charset = "UTF-8";
        val boundary = "===" + System.currentTimeMillis() + "===";

        val lineFeed = "\r\n";
        OutputStream outputStream = null;
        @Cleanup PrintWriter writer = null;
        try {
            this.httpURLConnection.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);
            outputStream = this.httpURLConnection.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(this.httpURLConnection.getOutputStream(), charset), true);
        } catch (IOException e) {
            log.error("Exception occurred while opening httpUrlConnection output stream", kv(EXCEPTION_LABEL_TAG, e.getMessage()), e);
            LogServiceUtil.errorMetrics(e);


        }

        for (String tmpFile : uploadFile) {
            val file = new File(tmpFile);
            val fileName = file.getName();

            assert writer != null;
            writer.append("--").append(boundary).append(lineFeed);
            writer.append("Content-Disposition: form-data; name=\"" + fileHolder + "\"; filename=\"").append(fileName).append("\"").append(lineFeed);
            writer.append("Content-Type: ").append(URLConnection.guessContentTypeFromName(fileName))
                    .append(lineFeed);
            writer.append("Content-Transfer-Encoding: binary").append(lineFeed);
            writer.append(lineFeed);
            writer.flush();
            try (val inputStream = new FileInputStream(file)) {
                val bytes = new byte[FOUR_KB];
                int bytesRead;
                while ((bytesRead = inputStream.read(bytes)) != -1) {
                    Objects.requireNonNull(outputStream).write(bytes, 0, bytesRead);
                }
                Objects.requireNonNull(outputStream).flush();

                writer.append(lineFeed);
                writer.flush();
                writer.append(lineFeed);
                writer.append("--").append(boundary).append("--").append(lineFeed);
                writer.flush();
                writer.close();
                log.info("Successfully upload file/resource");
            } catch (IOException e) {
                log.error("Exception occurred while sending file to the server", kv(EXCEPTION_LABEL_TAG, e.getMessage()), e);
                LogServiceUtil.errorMetrics(e);
            }
        }
        return this;
    }

    public GenericUrlConnectionBuilder getFileOutput(String path) throws IOException {
        log.trace(INSIDE_METHOD, "getFileOutput");
        setRequestPropertyKeyValue();
        this.statusCode = httpURLConnection.getResponseCode();

        log.info("Inside @method getFileOutput  @statusCode", kv(STATUS_CODE, this.statusCode));
        @Cleanup InputStream inputStream = this.httpURLConnection.getInputStream();

        try (val fileOutputStream = new FileOutputStream(path)) {
            int read;
            val bytes = new byte[FOUR_KB];
            while ((read = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, read);
            }

            inputStream.close();

            this.data = String.valueOf(inputStream);
            log.info("Inside @method getFileOutput :: Image is ready to download........");

            return this;
        }
    }

    public GenericUrlConnectionBuilder getFileOutput() {
        log.trace(INSIDE_METHOD, "getFileOutput");
        setRequestPropertyKeyValue();
        try {
            this.statusCode = httpURLConnection.getResponseCode();
            log.info("Inside @method getFileOutput  @statusCode", kv(STATUS_CODE, this.statusCode));
            this.data = this.httpURLConnection.getInputStream();
        } catch (IOException e) {
            log.error("Exception occurred while getting file from the server", kv(EXCEPTION_LABEL_TAG, e.getMessage()), e);
            LogServiceUtil.errorMetrics(e);

        }

        return this;
    }

    private void getResponseData() throws IOException {
        log.trace(INSIDE_METHOD, "getResponseData");
        if (this.httpURLConnection.getInputStream() != null) {
            val in = new BufferedReader(new InputStreamReader(this.httpURLConnection.getInputStream()));
            val response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            this.data = String.valueOf(response);
        }

    }

    public GenericUrlConnectionBuilder setRequestBody(Object outputData) {
        log.trace(INSIDE_METHOD, "setRequestBody");
        this.httpURLConnection.setDoOutput(true);

        try (val os = this.httpURLConnection.getOutputStream()) {
            val input = String.valueOf(outputData).getBytes();
            os.write(input, 0, input.length);
            log.info("Successfully send request body");
        } catch (IOException e) {
            log.error("Exception occurred while sending request body", kv(EXCEPTION_LABEL_TAG, e.getMessage()), e);
            LogServiceUtil.errorMetrics(e);
        }
        return this;
    }


    public GenericUrlConnectionBuilder setRequestProperty(Map<String, String> headers) {
        log.trace(INSIDE_METHOD, "setRequestProperty");
        if (headers != null) {
            headers.forEach((key, value) -> {
                this.requestPropertyKey.add(key);
                this.requestPropertyValue.add(value);
            });
            setRequestPropertyKeyValue();
        }

        return this;
    }

    public GenericUrlConnection build() {
        return new GenericUrlConnection(this);
    }

}
