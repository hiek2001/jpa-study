package jpabook.jpashop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AddressEntity {
    // 실무에서는 이 방법을 주로 사용
    @Id @GeneratedValue
    private Long id;

    private Address address;


    public AddressEntity(String city, String street, String zipcode) {
       this.address = new Address(city, street, zipcode);

    }

    public AddressEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
