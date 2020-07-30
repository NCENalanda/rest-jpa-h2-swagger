package eternal.hoge.spring.boot.example.simple.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import static  com.example.demo.utility.Constant.*;


public class Test {
	//https://60.254.111.202:18244/token
     String hostUrl="https://60.254.111.202:19444";
     String tokenUrl="https://60.254.111.202:18244";
     String createToken=null;
     String publishToken=null;
     String subToken=null;
     String manageToken=null;
     String deleteAPIToken=null;
     String viewApiToken=null;
     String serviceToken=null;
	private static CloseableHttpClient getApacheSslBypassClient()
			throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException {
		return HttpClients.custom().setHostnameVerifier(new AllowAllHostnameVerifier())
				.setSslcontext(new SSLContextBuilder().loadTrustMaterial(null, (arg0, arg1) -> true).build()).build();
	}
	Test(){
		//sendbox OFZBNE9EZ054UlhmdmpqYW5Pam12ZHZtZ1lNYTpKX1AwVnFWYnlZRXQyTFBZd0NwNmxibEhJZzRh
		//single node
		//serviceToken="MG5QZlpJZFFlWEhJUjNHU0VOMGJoZ1I4VGFvYTpFcHdUX3F2cXphZVE1YXBKMDJJak9naEtBX3Nh";
		
		serviceToken="czJleVpIdnRIQnZybFhtcW9MOU9UaFl3ZlFRYTpTSEhZQ2l3RTI5UEd5WUQ2b2JhMlNkNXJTa0Vh";
		//createToken=getWso2Token("apim:api_create");
		//publishToken=getWso2Token("apim:api_publish");
		//subToken=getWso2Token("apim:subscribe");
		//manageToken=getWso2Token("apim:app_manage");
		deleteAPIToken=getWso2Token("apim:api_delete");	
		//viewApiToken=getWso2Token("apim:api_view");

		System.out.println("createToken : "+createToken);
		System.out.println("publishToken : "+publishToken);
		System.out.println("subToken : "+subToken);
		System.out.println("deleteAPIToken : "+deleteAPIToken);
		System.out.println("viewApiToken : "+viewApiToken);

	}
	
	private static  JSONObject extractedToken(String aouthToken) throws ParseException {
		String array[] = aouthToken.split("\\.");
		String index = array[1];
		byte[] decodedBytes = Base64.getDecoder().decode(index);
		String decodedString = new String(decodedBytes);
		System.out.println("decodedString==="+decodedString);
		JSONParser parser = new JSONParser();
		org.json.simple.JSONObject json = (org.json.simple.JSONObject) parser.parse(decodedString);
		System.out.println("extractedToken==="+json);
		return new JSONObject(json.toString());
	}

	static List<String[]> allData ;
	//static  Test test;
	public static void main(String gg[]) throws ParseException {
		
		/*String token="eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJDOG9Tb0lRd1BkcnlMU05OemJjY2xuQUdyLWFva29uQk9KSWMtNWJYbkFBIn0.eyJleHAiOjE1ODg3NjkxNjAsImlhdCI6MTU4ODc2ODg2MCwianRpIjoiZTBiNjkxYzUtN2ZmOC00MzEyLWFiZmUtYTJjNGQyMTAxNTk0IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2F1dGgvcmVhbG1zL3dzbzItVUkiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiNGRkMTgyNzgtMjRhOC00MDQ4LTgxMjQtMjE2OWM2OGNkNWY2IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiV1NPMi1BUElNIiwic2Vzc2lvbl9zdGF0ZSI6ImM0ODUzYjcwLTk0ODgtNDQwNi1hMTM1LTE1ZjQxYWI4MDU2ZCIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiY3JlYXRvciIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJzdWIiOiJjcmVhdG9yIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJjcmVhdG9yIn0.IhKxsC9GW9vBtyVVuGM8HkyxHuGbt-4m2tiZODfMnQR2Vj5qhBQvyqPlwGWg8sRp8N1Clo0tV5DJ7TX-0E5_GCqkA4JQkol5EeesiP18YIpnt5eZ6k53QwxCcBAjB8ELfBUe8x00AiSK1roqdBUxhgTphaUFbyzRA9MVYCUylfqfJ05t9B1LK_1n75BQtdbd7Zzid-bzeBT0C9fB5q59ZpY12wjYqyygMOar5hoV_2Y8AN1QH-94qa0vJ9DZ9cxJ9S04r6a8t-5H4m5-WQYpkawQR1esThNHITRyUtjDbS-tFPFPq-k1UrE2V9kUsk1MlGkZqzUuN0D8IHQZsE2ZQg";
		JSONObject tokenJson=extractedToken(token);
		System.out.println("extractedToken==="+tokenJson);
		
		
		Test test=new Test();

		test.deleteAPI("00077d93-d754-4464-a52d-7b6cde57d2fa");*/

		// test=new Test();
		String  file = "/home/ist/Documents/API-LIST3RD.csv";// "/home/ist/APILIST-2ND.csv";//"/home/ist/API_LIST.csv";

		readAllDataAtOnce(file);

		//test.deleteData();
		
		//Map<String,Set<String>> appData=test.createAPI();
		//test.createApplication(appData);
		
		
	}

	public static void readAllDataAtOnce(String file)
	{
		int i =1;
		try {
			// Create an object of file reader
			// class with CSV file as a parameter.
			FileReader filereader = new FileReader(file);

			// create csvReader object and skip first Line
			CSVReader csvReader = new CSVReaderBuilder(filereader)
					.withSkipLines(1)
					.build();
			 allData = csvReader.readAll();

			// print Data
			for (String[] row : allData) {
				/*for (String cell : row) {
					System.out.print(cell + "\t" +" NO "+i);
					System.out.print(cell + "\t" +" NO "+i);
					i++;

				}*/
				System.out.print(row[0] + "\t" +" NO "+i + "process "+ "\t \t \t");

				Test test = new Test();
				test.deleteAPI(row[0]);
				System.out.print(row[0] + "\t" +" NO "+i + "complete ");
				i++;
				System.out.println();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String,Set<String>> createAPI() {
		JSONArray wsoApis=getApimanJson();
		System.out.print("wsoApis==="+wsoApis);
		Map<String,String> apiData=new HashMap<>();
		Map<String,Set<String>> appData=new HashMap<>();
		for(int i=0;i<wsoApis.length();i++) {
			System.out.print("wsoApis==="+wsoApis.length());
			JSONObject apiJson=wsoApis.getJSONObject(i);
			JSONArray cliList=apiJson.getJSONArray("client_name");
			apiJson.remove("client_name");
			System.out.println(apiJson.getString("name"));
			if(apiData.containsKey(apiJson.getString("name"))) {
				System.out.println("already==="+apiJson.getString("name"));
				for(int j=0;j<cliList.length();j++) {
					String clientName=cliList.getString(j); 
					if(appData.containsKey(clientName)) {
						appData.get(clientName).add(apiData.get(apiJson.getString("name")));
					}else {
						Set<String> apiIds=new HashSet<>();
						apiIds.add(apiData.get(apiJson.getString("name")));
						appData.put(clientName,apiIds);
					}
				}
			}else {
			 apiJson=createAPI(wsoApis.getJSONObject(i));
				if(apiJson!=null) {
					System.out.println("create==="+apiJson.getString("name"));
					apiData.put(apiJson.getString("name"),apiJson.getString("id"));
					publishAPI(apiJson.getString("id"));
					for(int j=0;j<cliList.length();j++) {
						String clientName=cliList.getString(j); 
						if(appData.containsKey(clientName)) {
							appData.get(clientName).add(apiJson.getString("id"));
						}else {
							Set<String> apiIds=new HashSet<>();
							apiIds.add(apiJson.getString("id"));
							appData.put(clientName,apiIds);
						}
					}
				}
			}
			
		}
		return appData;
	}
	
	private void createApplication(Map<String, Set<String>> appData) {
		Map<String,String> appList=getAllApplication();
		for (Map.Entry<String,Set<String>> entry:appData.entrySet())  {
			System.out.print("createApplication==="+appData.size());
			String appName=entry.getKey();
			String appId=null;
			if(!appList.containsKey(appName)) {
			   appId=createWso2Application(subToken,appName);
			}else {
			   appId=appList.get(appName);
			}
			if(appId!=null) {
				Set<String> apiIds=entry.getValue();
				for(String apiId:apiIds) {
					subscribeAPiS(apiId,"Unlimited",appId,subToken);
				}
			}
			
   		}
    } 
	
	private Map<String,String> getAllApplication(){
		    Map<String,String> appList=new HashMap<>();
			try (CloseableHttpClient httpClient = getApacheSslBypassClient()) {
				String endpointUrl = hostUrl+"/api/am/store/v1/applications?sortBy=name&sortOrder=asc&limit=1000&offset=0";
				System.out.println("endpointUrl " + endpointUrl);
				HttpGet httpget = new HttpGet(endpointUrl);
				httpget.setHeader(HTTP_HEADER_CONTENT_TYPE, "application/json;charset=UTF-8");
				//Authorization: Bearer <token>
				httpget.setHeader(AUTHORIZATION, "Bearer " + subToken);
				BufferedReader reader = null;
				HttpResponse response = httpClient.execute(httpget);
				int statusCode = response.getStatusLine().getStatusCode();
				HttpEntity entity = response.getEntity();
				reader = new BufferedReader(new InputStreamReader(entity.getContent(), UTF_8));
				System.out.println("statusCode : " + statusCode);
				JSONObject responseObject = getParsedObjectByReader(reader);
				
				System.out.println("responseObject : " + responseObject);
				JSONArray dataArray=responseObject.getJSONArray("list");
				for(int i=0;i<dataArray.length();i++) {
					appList.put(dataArray.getJSONObject(i).getString("name"),dataArray.getJSONObject(i).getString("applicationId"));
				}
				
				return appList;//;
			} catch (Exception e) {
				e.printStackTrace();System.out.println("createWso2Application : " + e.getMessage());
				return appList;
			}
	}
	
	private String createWso2Application(String token, String appName) {
		try (CloseableHttpClient httpClient = getApacheSslBypassClient()) {
			String endpointUrl = hostUrl+"/api/am/store/v1/applications";
			System.out.println("endpointUrl " + endpointUrl);
			String Json = "{\"name\":\"ewdewew\",\"throttlingPolicy\":\"Unlimited\",\"description\":\"\",\"tokenType\":\"OAUTH\",\"groups\":null,\"attributes\":{}}";
			JSONObject data = new JSONObject(Json);
			data.put("name", appName);
			Json = data.toString();
			StringEntity entity1 = new StringEntity(Json);
			HttpPost httpPost = new HttpPost(endpointUrl);
			httpPost.setEntity(entity1);
			httpPost.setHeader(HTTP_HEADER_CONTENT_TYPE, "application/json;charset=UTF-8");
			httpPost.setHeader(AUTHORIZATION, "Bearer " + token);
			BufferedReader reader = null;
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			reader = new BufferedReader(new InputStreamReader(entity.getContent(),UTF_8));
			JSONObject responseObject = getParsedObjectByReader(reader);
			System.out.println("statusCode : " + statusCode);
			System.out.println("responseObject : " + responseObject);
			return responseObject.getString("applicationId");
		} catch (Exception e) {
			System.out.println("createWso2Application : " + e.getMessage());
			return null;
		}
	}
	
	private void subscribeAPiS(String apiId, String throttlingPolicy, String applicationId,String token) {
		try (CloseableHttpClient httpClient = getApacheSslBypassClient()) {
			String endpointUrl = hostUrl+"/api/am/store/v1/subscriptions";
			System.out.println("endpointUrl " + endpointUrl);
			
			JSONObject data = new JSONObject();
			data.put("apiId", apiId);
			data.put("throttlingPolicy", throttlingPolicy);
			data.put("applicationId", applicationId);
			StringEntity entity1 = new StringEntity(data.toString());
			HttpPost httpPost = new HttpPost(endpointUrl);
			httpPost.setEntity(entity1);
			httpPost.setHeader(HTTP_HEADER_CONTENT_TYPE, "application/json;charset=UTF-8");
			httpPost.setHeader(AUTHORIZATION, "Bearer " + token);
			BufferedReader reader = null;
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			reader = new BufferedReader(new InputStreamReader(entity.getContent(), UTF_8));
			JSONObject responseObject = getParsedObjectByReader(reader);
			System.out.println("statusCode : " + statusCode);
			System.out.println("responseObject : " + responseObject);
		} catch (Exception e) {
			System.out.println("createWso2Application : " + e.getMessage());
		}
		
	}
	
	
	

	private JSONObject getAPIJSON(JSONObject apibean, JSONObject apiVersionBean) {
		if(apibean.has("name") && apiVersionBean.has("endpoint")) {
			JSONObject apiJson=new JSONObject();
			apiJson.put("name",apibean.getString("name"));
			apiJson.put("version","1");
			apiJson.put("context","gateway/"+apibean.getString("id").replaceAll("\\s", ""));
			JSONArray policies=new JSONArray();
			policies.put("Unlimited");
			apiJson.put("policies",policies);
			JSONObject endpointConfig=new JSONObject();
			endpointConfig.put("endpoint_type", "http");
			JSONObject sandbox_endpoints=new JSONObject();
			sandbox_endpoints.put("url", apiVersionBean.getString("endpoint"));
			JSONObject production_endpoints=new JSONObject();
			production_endpoints.put("url", apiVersionBean.getString("endpoint"));
			endpointConfig.put("sandbox_endpoints", sandbox_endpoints);
			endpointConfig.put("production_endpoints", production_endpoints);
			apiJson.put("endpointConfig",endpointConfig);
			JSONArray gatewayEnvironments=new JSONArray();
			gatewayEnvironments.put("Production and Sandbox");
			apiJson.put("gatewayEnvironments",gatewayEnvironments);
			
			/*JSONArray scopes=new JSONArray();
			JSONObject scope=new JSONObject();
			scope.put("name","createScope12");
			JSONObject bindings=new JSONObject();
			bindings.put("type","role");
			JSONArray values=new JSONArray();
			bindings.put("values",values);
			scope.put("bindings",bindings);
			scopes.put(scope);
			apiJson.put("scopes", scopes);
			*/
			
			JSONArray securityScheme=new JSONArray();
			securityScheme.put("oauth_basic_auth_api_key_mandatory");
			securityScheme.put("basic_auth");
			if(!apiVersionBean.getBoolean("publicAPI")) {
				securityScheme.put("api_key");	
			}
			
			apiJson.put("securityScheme",securityScheme);
			
			/*JSONArray operations=new JSONArray();
			JSONObject operation=new JSONObject();
			operation.put("id","");
			operation.put("target","/*");
			operation.put("verb","GET");
			operation.put("authType","Application & Application User");
			operation.put("throttlingPolicy", "Unlimited");
			
			JSONArray resScopes=new JSONArray();
			resScopes.put("createScope12");
			operation.put("scopes", resScopes);
			
			JSONArray usedProductIds=new JSONArray();
			operation.put("usedProductIds", usedProductIds);
			operations.put(operation);
			
			JSONObject operation2=new JSONObject();
			operation2.put("id","");
			operation2.put("target","/*");
			operation2.put("verb","PUT");
			operation2.put("authType","Application & Application User");
			operation2.put("throttlingPolicy", "Unlimited");
			//operation2.put("scopes", resScopes);
			operation2.put("usedProductIds", usedProductIds);
			operations.put(operation2);
			
			
			apiJson.put("operations", operations);
			*/
			
			
			
			return apiJson;
		}else {
			return null;
		}
		
	}

	private JSONObject publishAPI(String apiId) {
		try (CloseableHttpClient httpClient = getApacheSslBypassClient()) {
			String url = hostUrl+"/api/am/publisher/v1/apis/change-lifecycle?action=Publish&apiId="+apiId;
			System.out.println("publishAPI url :  " + url);
			HttpPost httpPost = new HttpPost(url);
			String body="{}";
			System.out.println("body :  " + body);
			httpPost.setEntity(new StringEntity(body.toString(), UTF_8));
			httpPost.setHeader(HTTP_HEADER_CONTENT_TYPE, "application/json");
			httpPost.setHeader(AUTHORIZATION,
					"Bearer "+publishToken);
			BufferedReader reader = null;
			HttpResponse response = httpClient.execute(httpPost);
			System.out.println(response);
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			if(statusCode==200) {
				HttpEntity entity = response.getEntity();
				reader = new BufferedReader(new InputStreamReader(entity.getContent(), UTF_8));
				JSONObject responseObject = getParsedObjectByReader(reader);
				System.out.println("publishAPI res"+responseObject);
				
				return responseObject;
			}else {
				return null;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public JSONObject createAPI(JSONObject apiJson) {
		try (CloseableHttpClient httpClient = getApacheSslBypassClient()) {
			String url = hostUrl+"/api/am/publisher/v1/apis?openAPIVersion=v3";
			System.out.println("createAPI url :  " + url);
			HttpPost httpPost = new HttpPost(url);
			System.out.println("apiJson :  " + apiJson.toString());
			httpPost.setEntity(new StringEntity(apiJson.toString(), UTF_8));
			httpPost.setHeader(HTTP_HEADER_CONTENT_TYPE, "application/json");
			httpPost.setHeader(AUTHORIZATION,
					"Bearer "+createToken);
			BufferedReader reader = null;
			HttpResponse response = httpClient.execute(httpPost);
			System.out.println(response);
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			if(statusCode==201) {
				HttpEntity entity = response.getEntity();
				reader = new BufferedReader(new InputStreamReader(entity.getContent(), UTF_8));
				JSONObject responseObject = getParsedObjectByReader(reader);
				System.out.println("createAPI res"+responseObject);
				
				return responseObject;
			}else {
				return null;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	 String getWso2Token(String scope) {
		try (CloseableHttpClient httpClient = getApacheSslBypassClient()) {
			String url = tokenUrl+"/token";
			System.out.println("getToken url :  " + url);
			HttpPost httpPost = new HttpPost(url);
			String body = "grant_type=password&username=admin&password=admin&scope="+scope;
			System.out.println("body :  " + body);
			httpPost.setEntity(new StringEntity(body, UTF_8));
			httpPost.setHeader(HTTP_HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded");

			httpPost.setHeader(AUTHORIZATION,
					"Basic "+serviceToken);
			BufferedReader reader = null;
			HttpResponse response = httpClient.execute(httpPost);
			System.out.println(response);
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			HttpEntity entity = response.getEntity();
			reader = new BufferedReader(new InputStreamReader(entity.getContent(), UTF_8));
			JSONObject responseObject = getParsedObjectByReader(reader);
			System.out.println("getWso2Token res"+responseObject);
			String token = responseObject.getString("access_token");

			return token;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public JSONArray getApimanJson() {
		JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("/home/innoeye/Downloads/kiba-api-manager-export.json")) {
        	org.json.simple.JSONObject parsedObject = (org.json.simple.JSONObject)jsonParser.parse(reader);
        	JSONObject apiJson=new JSONObject(parsedObject.toJSONString());
        	JSONArray wso2array=new JSONArray();
        	JSONArray orgs=apiJson.getJSONArray("Orgs");
        	for(int i=0;i<orgs.length();i++) {
        		JSONObject org=orgs.getJSONObject(i);
        		JSONArray apis=org.getJSONArray("Apis");
        		JSONArray clients=org.getJSONArray("Clients");
        		Map<String,List<String>> cData=getClientMap(clients);
        		for(int y=0;y<apis.length();y++) {
        			JSONObject api=apis.getJSONObject(y);
        			JSONObject apibean=api.getJSONObject("ApiBean");
        			JSONObject apiVersionBean=api.getJSONArray("Versions").getJSONObject(0).getJSONObject("ApiVersionBean");
        			//System.out.println("==apibean="+apibean);
        			//System.out.println("==apiVersionBean="+apiVersionBean);
        			
        			JSONObject wso2API=getAPIJSON(apibean,apiVersionBean);
        			
        			
        			if(wso2API!=null) {
        				if(cData.get(apibean.getString("id"))!=null) {
        					wso2API.put("client_name",cData.get(apibean.getString("id")));
        				}else {
        					List<String> cli=new ArrayList<>();
        					cli.add(org.getJSONObject("OrganizationBean").getString("name"));
        					wso2API.put("client_name",cli);
        				}
        				if(org.getJSONObject("OrganizationBean").getString("name").equalsIgnoreCase("ossticket")) {
        					JSONObject additionalProperties=new JSONObject();
        					additionalProperties.put("clientName","clientName");
        					wso2API.put("additionalProperties", additionalProperties);
        				}
        				wso2array.put(wso2API);
        			}
        			//System.out.println("==wso2API="+wso2API);
        			
        		}
        	}
        	return wso2array;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
	}
	
	private Map<String, List<String>> getClientMap(JSONArray clients) {
		Map<String,List<String>> cMap=new HashMap<>();
		for(int y=0;y<clients.length();y++) {
			JSONObject client=clients.getJSONObject(y);
			String cname=client.getJSONObject("ClientBean").getString("name");
			JSONArray contracts=client.getJSONArray("Versions").getJSONObject(0).getJSONArray("Contracts");
			for(int i=0;i<contracts.length();i++) {
				JSONObject api=contracts.getJSONObject(i).getJSONObject("api").getJSONObject("api");
				String apiId=api.getString("id");
				if(cMap.containsKey(apiId)) {
					cMap.get(apiId).add(cname);
				}else {
					List<String> cli=new ArrayList<>();
					cli.add(cname);
					cMap.put(apiId, cli);
				}
				
			}	
		}
		return cMap;
	}

	private JSONObject getParsedObjectByReader(BufferedReader reader) throws ParseException, IOException {
		org.json.simple.JSONObject parsedObject = null;
		JSONParser parser = new JSONParser();
		if (reader != null) {
			parsedObject = (org.json.simple.JSONObject) parser.parse(reader);
		}
		return new JSONObject(parsedObject.toJSONString());
	}
	
	public JSONObject uploadPolicies(String apiId, String token){
		try (CloseableHttpClient httpClient = getApacheSslBypassClient()) {
			String url=hostUrl+"/api/am/publisher/v1/apis/"+apiId+"/mediation-policies";
		HttpPost httpPost = new HttpPost(url);
		File file = new File("/home/innoeye/Downloads/custom.xml");
		FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("mediationPolicyFile", fileBody);
		httpPost.setHeader(HTTP_HEADER_CONTENT_TYPE, "multipart/form-data");
		httpPost.setHeader(AUTHORIZATION,
				"Bearer "+token);
		HttpEntity entity = builder.build();
		httpPost.setEntity(entity);
		HttpResponse response =httpClient.execute(httpPost);
			
	    System.out.println(response); int statusCode =
		response.getStatusLine().getStatusCode(); System.out.println(statusCode);
		HttpEntity resentity = response.getEntity();
		
		BufferedReader reader = new BufferedReader(new
		InputStreamReader(resentity.getContent(), UTF_8)); JSONObject
		responseObject = getParsedObjectByReader(reader);
		System.out.println("uploadPolicies res"+responseObject);
		
		return null;//responseObject;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private void deleteData() {
		Map<String,String> apps=getAllApplication();
		if(!apps.isEmpty()) {
			for(String appId:apps.values()){
				deleteApp(appId);
			}
		}
		
		List<String> apis= getAllApis();
		if(!apis.isEmpty()) {
		for(String apiId:apis){
			deleteAPI(apiId);
		}
		}
	}
	
	private void deleteAPI(String apiId) {
		System.out.println("deleteAPI  API-ID : " + apiId);
		try (CloseableHttpClient httpClient = getApacheSslBypassClient()) {
			String endpointUrl = hostUrl+"/api/am/publisher/v1/apis/"+apiId;
			System.out.println("endpointUrl " + endpointUrl);
			HttpDelete httpPost = new HttpDelete(endpointUrl);
			httpPost.setHeader(HTTP_HEADER_CONTENT_TYPE, "application/json;charset=UTF-8");
			httpPost.setHeader(AUTHORIZATION, "Bearer " + deleteAPIToken);
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println("statusCode : " + statusCode);
			System.out.println("delete Api Process complete");
		} catch (Exception e) {
			System.out.println("deleteApp : " + e.getMessage());
		}
	}
	
	private List<String> getAllApis(){
		List<String> apis=new ArrayList<>();
		try (CloseableHttpClient httpClient = getApacheSslBypassClient()) {
			String endpointUrl = hostUrl+"/api/am/publisher/v1/apis?limit=1000&offset=0";
			System.out.println("endpointUrl " + endpointUrl);
			HttpGet httpget = new HttpGet(endpointUrl);
			httpget.setHeader(HTTP_HEADER_CONTENT_TYPE, "application/json;charset=UTF-8");
			httpget.setHeader(AUTHORIZATION, "Bearer " + viewApiToken);
			BufferedReader reader = null;
			HttpResponse response = httpClient.execute(httpget);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			reader = new BufferedReader(new InputStreamReader(entity.getContent(), UTF_8));
			System.out.println("statusCode : " + statusCode);
			JSONObject responseObject = getParsedObjectByReader(reader);
			
			System.out.println("responseObject : " + responseObject);
			JSONArray dataArray=responseObject.getJSONArray("list");
			for(int i=0;i<dataArray.length();i++) {
				apis.add(dataArray.getJSONObject(i).getString("id"));
			}
			
			return apis;//;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("createWso2Application : " + e.getMessage());
			return apis;
		}
}
	private void deleteApp(String applicationId) {
		try (CloseableHttpClient httpClient = getApacheSslBypassClient()) {
			String endpointUrl = hostUrl+"/api/am/store/v1/applications/"+applicationId;
			System.out.println("endpointUrl " + endpointUrl);
			HttpDelete httpPost = new HttpDelete(endpointUrl);
			httpPost.setHeader(HTTP_HEADER_CONTENT_TYPE, "application/json;charset=UTF-8");
			httpPost.setHeader(AUTHORIZATION, "Bearer " + manageToken);
			HttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println("statusCode : " + statusCode);
		} catch (Exception e) {
			System.out.println("deleteApp : " + e.getMessage());
		}
		
	}
}
