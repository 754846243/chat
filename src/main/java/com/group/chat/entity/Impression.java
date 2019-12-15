package iscyf.chatroom.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Data
public class Impression {

    /* 印象id */
    @Id
    @GeneratedValue
    private Integer id;

    /* 被描述的用户的id */
    private Integer uid;

    /* 印象 */
    private String impression;

    public Impression() {
    }

    public Impression(Integer uid, String impression) {
        this.uid = uid;
        this.impression = impression;
    }


}
