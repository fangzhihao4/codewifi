package codewifi.response.meeting;

import lombok.Data;

import java.util.List;

@Data
public class MeetingUserInfoResponse {
    String department; //部门
    String username; //姓名
    String phone; //电话号码
    String typeName; //用户类型
    String companyName; //公司名称
    String companyNo; //公司编号
    List<CompanyList> companyList;

    @Data
    public static class CompanyList{
        String companyName; //公司名称
        String companyNo; //公司编号
    }
}
