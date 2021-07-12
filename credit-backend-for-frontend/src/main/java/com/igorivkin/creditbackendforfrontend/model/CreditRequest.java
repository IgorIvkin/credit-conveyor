package com.igorivkin.creditbackendforfrontend.model;

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
    private Long requestedAmount = 0L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditRequest that = (CreditRequest) o;
        return Objects.equals(uuid, that.uuid)
                && Objects.equals(name, that.name)
                && Objects.equals(age, that.age)
                && Objects.equals(requestedAmount, that.requestedAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, age, requestedAmount);
    }

    @Override
    public String toString() {
        return "CreditRequest{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", requestedAmount=" + requestedAmount +
                '}';
    }
}
