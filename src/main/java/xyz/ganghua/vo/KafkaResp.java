package xyz.ganghua.vo;

import java.util.List;

public class KafkaResp extends BaseView<String> {

    private List<String> Lists;

    public List<String> getLists() {
        return Lists;
    }

    public void setLists(List<String> lists) {
        Lists = lists;
    }
}
