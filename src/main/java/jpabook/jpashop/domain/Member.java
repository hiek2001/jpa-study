package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Member { // @Table 옵션의 index도 넣고, @Column의 length 제한도 넣기. 그래야 개발자들끼리 보기가 편함.

   @Id @GeneratedValue
   @Column(name = "MEMBER_ID")
    private Long id;

   @Column(name = "USERNAME")
   private String username;

    // 응집성 있게 활용 가능함
   // 주소
   @Embedded
   private Address homeAddress;

   @Embedded
   @AttributeOverrides({
           @AttributeOverride(name="city", column = @Column(name = "WORK_CITY")),
           @AttributeOverride(name="street", column = @Column(name = "WORK_STREET")),
           @AttributeOverride(name="zipcode", column = @Column(name = "WORK_ZIPCODE")),
   })
   private Address workAddress;

   // @CollectionTable 로 인해 해당 table 이 자동으로 만들어짐
   @ElementCollection
   @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
        @JoinColumn(name = "MEMBER_ID")
   )
   @Column(name = "FOOD_NAME")  // FK 말고 PK인 값 하나만 있기 때문에 연결
   private Set<String> favoriteFoods = new HashSet<>();

   // 연관된 데이터 모두 삭제 후, 현재 값을 다시 삽입함. 운영 X
    // member_id가 모두 동일하여 데이터 추적이 어려움
//   @ElementCollection
//   @CollectionTable(name = "ADDRESS", joinColumns =
//        @JoinColumn(name = "MEMBER_ID")
//   )
//   private List<Address> addressHistory = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="MEMBER_ID")    // 일대다 단방향 매핑으로 사용하기, 컬렉션 타입 사용하는것보다 쿼리 최적화가 훨씬 유리. 컬렉션 타입은 왠만하면 사용 X
    private List<AddressEntity> addressHistory = new ArrayList<>();
    // entity로 만들어 사용하면 member_id도 별도로 들어감. 값을 가져와서 수정하는것이 가능해짐

    //
    // Period
   @Embedded
   private Period workPeriod;

   @OneToMany(mappedBy = "member")
   private List<Order> orders = new ArrayList<>(); // 여기서 list를 쓰는건, 좋은 설계는 아님

//   @Column(name = "USERNAME")
//   private String username;
//   private int age;
//
    @ManyToOne // member 입장 : n, team 입장 : 1
    @JoinColumn(name="TEAM_ID")
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void changeTeam(Team team) { // setTeam
        this.team = team;
        team.getMembers().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
    public void setTeam(Team team) {
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public Address getWorkAddress() {
        return workAddress;
    }
    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }
}
