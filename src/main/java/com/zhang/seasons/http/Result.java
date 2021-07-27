package com.zhang.seasons.http;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {
    private static final String CODE_TAG = "code";
    private static final String STATUS_TAG = "status";
    private static final String SUCCESS_TAG = "success";
    private static final String ERROR_TAG = "error";
    private static final String RESULT_TAG = "result";

    public static Result error(String msg) {
        Result result = new Result();
        result.put(CODE_TAG, 1);
        result.put(STATUS_TAG, ERROR_TAG);
        result.put(RESULT_TAG, msg);
        return result;
    }

    public static Result success(Object obj) {
        Result result = new Result();
        result.put(CODE_TAG, 0);
        result.put(STATUS_TAG, SUCCESS_TAG);
        result.put(RESULT_TAG, obj);
        return result;
    }

    public static Result success() {
        return success(null);
    }
}
