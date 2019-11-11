package TableModel;

public class MemberTM {
    private String memberId;
    private String name;
    private String address;
    private String contactNo;

    public MemberTM() {
    }

    public MemberTM(String memberId, String name, String address, String contactNo) {
        this.memberId = memberId;
        this.name = name;
        this.address = address;
        this.contactNo = contactNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
}
