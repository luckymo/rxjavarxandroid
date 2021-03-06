package test.com.rxjavarxandroid.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import test.com.rxjavarxandroid.entity.ChannleEntity;

import test.com.rxjavarxandroid.dao.ChannleEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig channleEntityDaoConfig;

    private final ChannleEntityDao channleEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        channleEntityDaoConfig = daoConfigMap.get(ChannleEntityDao.class).clone();
        channleEntityDaoConfig.initIdentityScope(type);

        channleEntityDao = new ChannleEntityDao(channleEntityDaoConfig, this);

        registerDao(ChannleEntity.class, channleEntityDao);
    }
    
    public void clear() {
        channleEntityDaoConfig.getIdentityScope().clear();
    }

    public ChannleEntityDao getChannleEntityDao() {
        return channleEntityDao;
    }

}
