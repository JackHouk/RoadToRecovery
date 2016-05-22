package jack.roadtorecovery;

/**
 * Created by Jack on 5/21/2016.
 * class to hold all info for a contact entry
 * there are no required fields
 */
public class ContactEntry {
    public String mName;
    public String mPhone;
    public String mGroup;

    ContactEntry(String name, String phone, String group){
        mName = name;
        mPhone = phone;
        mGroup = group;

    }
}
