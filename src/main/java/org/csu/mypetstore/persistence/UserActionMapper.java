package org.csu.mypetstore.persistence;

import org.apache.ibatis.annotations.Param;

public interface UserActionMapper {

    public void recordAction(@Param("username") String username, @Param("action") String action, @Param("object") String object, @Param("date") String date);

    public String action(@Param("action") String action, @Param("object") String object);
}
