package dailynailheroku.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "stats")
public class StatsEntity extends BaseEntity{
    private int authorizedRequests;
    private int unauthorizedRequests;

    public StatsEntity() {
    }

    @Column(nullable = false)
    public int getAuthorizedRequests() {
        return authorizedRequests;
    }

    public StatsEntity setAuthorizedRequests(int authorizedRequests) {
        this.authorizedRequests = authorizedRequests;
        return this;
    }

    @Column(nullable = false)
    public int getUnauthorizedRequests() {
        return unauthorizedRequests;
    }

    public StatsEntity setUnauthorizedRequests(int unauthorizedRequests) {
        this.unauthorizedRequests = unauthorizedRequests;
        return this;
    }

}
