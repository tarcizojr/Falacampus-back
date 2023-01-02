package br.edu.ifpb.dac.falacampus.business.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import br.edu.ifpb.dac.falacampus.business.service.ConverterService;
import br.edu.ifpb.dac.falacampus.business.service.SuapService;

@Service
public class SuapServiceImpl implements SuapService {
	
	@Autowired
	private ConverterService converterService;

	@Override
	public String login(String username, String password) {
				
		Map body = Map.of(USERNAME_JSON_FIELD, username, PASSWORD_JSON_FIELD, password);
		
		String json = converterService.mapToJson(body);
		// mapTOJson(body);
		try {
			HttpRequest url = generatePostUrl(OBTAIN_TOKEN_URL, null, json);
			return sendRequest(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (InterruptedException e3) {
			e3.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String findEmployee(String token, String username) {
		String url = String.format("%s?search=%s", EMPLOYEES_URL, username);
		return find(token, url);
	}

	@Override
	public String findEmployee(String token) {
		return null;
	}

	@Override
	public String findStudent(String token, String username) {
		String url = String.format("%s?search=%s", STUDENTS_URL, username);
		return find(token, url);
	}

	@Override
	public String findStudent(String token) {
		return null;
	}

	public String findUser(String token, String username) {
		
		String result = findEmployee(token, username);
		if(result.contains("\"count\":0")) {
			result = findStudent(token, username);
		}
		return result;
	}
	
	private String find(String token, String findUrl) {
		System.out.println("TOKEN FIND"+token);	
		System.out.println("URL FIND"+findUrl);	
		
	
		try {
			HttpRequest url = generateGetUrl(findUrl,
				Map.of(TOKEN_HEADER_NAME, String.format(TOKEN_HEADER_VALUE, token)));
			
			System.out.println("URL "+url.headers());
			return sendRequest(url);
		} catch (URISyntaxException e) {
			e.getMessage();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (InterruptedException e3) {
			e3.printStackTrace();
		}
		
		return null;
	}
	
	public boolean isValidToken(String token) {
		Map body = Map.of(TOKEN_JSON_FIELD, token);

		String json = converterService.mapToJson(body);

		try {
			HttpRequest url = generatePostUrl(VERIFY_TOKEN_URL, null, json);
			String result = sendRequest(url);
			
			if(result.equals(token)) {
				return true;
			}

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (InterruptedException e3) {
			e3.printStackTrace();
		}

		return false;
	}

	private HttpRequest generatePostUrl(String url, Map<String, String> headers, String body) throws URISyntaxException {
		
		Builder builder = HttpRequest.newBuilder().uri(new URI(url));

		if (DEFAULT_HEADERS != null) {
			for (Map.Entry<String, String> header : DEFAULT_HEADERS.entrySet()) {
				builder.setHeader(header.getKey(), header.getValue());
			}
		}

		if (headers != null) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				builder.setHeader(header.getKey(), header.getValue());
			}
		}

		HttpRequest request = builder.POST(BodyPublishers.ofString(body)).build();

		return request;
	}
	
	private HttpRequest generateGetUrl(String url, Map<String, String> headers) throws URISyntaxException {
		
		Builder builder = HttpRequest.newBuilder().uri(new URI(url));

		for (Map.Entry<String, String> header : DEFAULT_HEADERS.entrySet()) {
			builder.setHeader(header.getKey(), header.getValue());
		}

		for (Map.Entry<String, String> header : headers.entrySet()) {
			builder.setHeader(header.getKey(), header.getValue());
		}

		HttpRequest request = builder.GET().build();

		return request;
	}

	private String sendRequest(HttpRequest httpRequest) throws IOException, InterruptedException {
		
		HttpClient httpClient = HttpClient.newHttpClient();
		String response = httpClient.send(httpRequest,HttpResponse.BodyHandlers.ofString()).body();
		return response;
	}

	
//----------------------------------
	@Override
	@Lazy
	public String findAllDepartament(String url) {
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo1OTMyNiwidXNlcm5hbWUiOiIyMDIwMTUwMjAwMzIiLCJleHAiOjE2NzE1Mzc4MjIsImVtYWlsIjoiIiwib3JpZ19pYXQiOjE2NzE0NTE0MjJ9.zhzZ8SYWpBorWX6M5Gg9UgqQepP6Sa9mz1TYuPMz8TM";
		
		String[] getIdFromUrl = url.split("v1/");
		String urlSon = getIdFromUrl[1];
		urlSon = urlSon.substring(0,urlSon.length()-1);
		return find(token, DEPARTAMENTS_URL + urlSon);
		
	}
	

	
	
}
