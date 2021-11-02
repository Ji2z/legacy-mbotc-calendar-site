package com.ssafy.mbotc.entity.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(value="notice content", description = "notice content needs for create mm post")
public class ReqNoticePost {

    @ApiModelProperty(name = "user name who want to notice")
    String user_name;

    @ApiModelProperty(name = "notice's content")
    String message;

    @ApiModelProperty(name = "current time")
    String time;

    @ApiModelProperty(name = "schedule start date&time (YYYY-MM-DD hh:mm)")
    String start_time;

    @ApiModelProperty(name = "schedule end date&time (YYYY-MM-DD hh:mm)")
    String end_time;

    @ApiModelProperty(name = "file id array which this notice has")
    String[] file_ids;

    @ApiModelProperty(name = "channel id to notice")
    String channel_id;

}
