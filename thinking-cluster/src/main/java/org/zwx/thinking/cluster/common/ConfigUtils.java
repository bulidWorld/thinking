package org.zwx.thinking.cluster.common;

import java.util.Map;

public interface ConfigUtils {

    String getConfig(String key);

    Map<String, Object> getMapConfig(String key);

}
