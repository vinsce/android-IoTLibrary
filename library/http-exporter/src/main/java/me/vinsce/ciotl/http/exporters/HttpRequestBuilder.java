package me.vinsce.ciotl.http.exporters;

import com.android.volley.Request;

/**
 * Interface for custom Volley HTTP Request builder
 *
 * @param <REQ> type of request body
 * @param <RES> type of response body
 */
public interface HttpRequestBuilder<REQ, RES> {
    Request<RES> buildRequest(REQ request);
}
