package test.com.rxjavarxandroid.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ChannleEntity {


    private Long chnId;
    private String chan_name;
    private Long create_time;
    private Integer sort;
    private Integer chn_type;
    private String url;


    @Generated(hash = 134485220)
    public ChannleEntity(Long chnId, String chan_name, Long create_time,
            Integer sort, Integer chn_type, String url) {
        this.chnId = chnId;
        this.chan_name = chan_name;
        this.create_time = create_time;
        this.sort = sort;
        this.chn_type = chn_type;
        this.url = url;
    }

    @Generated(hash = 1512460624)
    public ChannleEntity() {
    }


    public Long getChnId() {
        return chnId;
    }

    public void setChnId(Long chnId) {
        this.chnId = chnId;
    }

    public String getChan_name() {
        return chan_name;
    }

    public void setChan_name(String chan_name) {
        this.chan_name = chan_name;
    }

    public Long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Long create_time) {
        this.create_time = create_time;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getChn_type() {
        return chn_type;
    }

    public void setChn_type(Integer chn_type) {
        this.chn_type = chn_type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ChannleEntity{" +
                "chnId=" + chnId +
                ", chan_name='" + chan_name + '\'' +
                ", create_time=" + create_time +
                ", sort=" + sort +
                ", chn_type=" + chn_type +
                ", url='" + url + '\'' +
                '}';
    }
}
