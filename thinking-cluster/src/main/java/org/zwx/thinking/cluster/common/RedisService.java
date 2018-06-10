package org.zwx.thinking.cluster.common;

import java.util.List;

public interface RedisService {

    void strSet(String key, String val);

    String strGet(String key);

    void lstAdd(String key, String body);

    List<String> lstGetAndRemove(String key);
}
