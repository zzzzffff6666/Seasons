package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Message;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

public interface MessageMapper {
    @Insert("insert into message(receiver, sender, sender_name, msg, url, read, created) " +
            "values(#{receiver}, #{sender}, #{senderName}, #{msg}, #{url}, 0, #{created})")
    int insert(Message message);

    @Insert("<script> " +
            "insert into message(receiver, sender, sender_name, msg, url, read, created) " +
            "values " +
            "<foreach collection='list' item='item' index='index' separator=','> " +
            "(#{item}, #{sender}, #{senderName}, #{msg}, #{url}, 0, #{created})" +
            "</foreach> " +
            "</script>")
    int insertByList(@Param("list") List<Integer> list, int sender, String senderName, String msg, String url, Timestamp now);

    @Delete("delete from message " +
            "where receiver = #{receiver} " +
            "and sender = #{sender} " +
            "and created = #{created}")
    int delete(int receiver, int sender, Timestamp created);

    @Update("update message " +
            "set read = 1 " +
            "where receiver = #{receiver} " +
            "and sender = #{sender} " +
            "and created = #{created}")
    int updateRead(Message message);

    @Update("update message " +
            "set read = 1 " +
            "where receiver = #{receiver} " +
            "and read = 0")
    int updateReadAllByReceiver(int receiver);

    @Select("select count(*) " +
            "from message " +
            "where receiver = #{receiver}")
    int selectUnReadMount(int receiver);

    @Select("select * " +
            "from message " +
            "where receiver = #{receiver} " +
            "order by created desc")
    @ResultType(Message.class)
    List<Message> selectByReceiver(int receiver);

    @Select("select * " +
            "from message " +
            "where sender = #{sender} " +
            "order by created desc")
    @ResultType(Message.class)
    List<Message> selectBySender(int sender);

    @Select("select * " +
            "from message " +
            "order by created desc")
    @ResultType(Message.class)
    List<Message> selectAll();
}
