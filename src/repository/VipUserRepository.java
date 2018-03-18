package repository;

import model.User;

import javax.persistence.EntityTransaction;
import javax.ws.rs.BadRequestException;

public class VipUserRepository extends UserRepository {
    public void sendCoin(int id, int coin) {
        User user = find(id);
        EntityTransaction tran = em.getTransaction();
        tran.begin();

        try {
            if (user.getCoin() >= coin) {
                user.setCoin(user.getCoin() - coin);
            } else {
                throw new BadRequestException();
            }
            tran.commit();
        } catch (Exception e) {
            tran.rollback();
            throw new BadRequestException();
        }
    }
}
