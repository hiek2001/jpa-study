package jpabook.jpashop.domain;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Period() {

    }

    public Period(LocalDateTime startDate, LocalDateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isWork(LocalDateTime startDate, LocalDateTime endDate) {
        // startDate와 endDate를 비교하여 어떤게 있는지 비교할 수 있음
        return true;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
