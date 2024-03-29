package com.igorivkin.creditconveyor.requestfilterservice.model.rabbit;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequest implements Serializable {
    private UUID uuid;
    private String name;
    private Integer age;
    private Long requestedAmount;
    private Long salary;
    private Integer months;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditRequest that = (CreditRequest) o;
        return Objects.equals(uuid, that.uuid)
                && Objects.equals(name, that.name)
                && Objects.equals(age, that.age)
                && Objects.equals(requestedAmount, that.requestedAmount)
                && Objects.equals(salary, that.salary)
                && Objects.equals(months, that.months);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, age, requestedAmount, salary, months);
    }

    @Override
    public String toString() {
        return "CreditRequest{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", requestedAmount=" + requestedAmount +
                ", salary=" + salary +
                ", months=" + months +
                '}';
    }
}

