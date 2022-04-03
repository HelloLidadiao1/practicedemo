package com.demo.util;

import com.demo.pojo.Staff;
import com.demo.pojo.StaffVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author lihongjie
 * @date 2022/3/31
 */
@Mapper(componentModel="spring")
public interface StaffTransfer {
    StaffTransfer INSTANCE = Mappers.getMapper(StaffTransfer.class);
    @Mappings({
        @Mapping(source = "uid", target = "uid"),
        @Mapping(source = "uname", target = "uname"),
        @Mapping(source = "idCard", target = "idCard"),
        @Mapping(source = "job", target = "job"),
        @Mapping(source = "bankAccount", target = "bankAccount"),
        @Mapping(source = "bankInfo", target = "bankInfo"),
        @Mapping(source = "telephoneNum", target = "telephoneNum")
    })
    StaffVO staff2VO(Staff staff);

    List<StaffVO> staff2VOs(List<Staff> staff);
}
