package com.dys.consul.servicemesh.mesh;

import com.ecwid.consul.v1.agent.model.NewService;
import com.google.gson.annotations.SerializedName;

/**
 * 增加了service mesh
 * 服务注册字段,字段参考https://www.consul.io/docs/connect/registration/sidecar-service
 */
public class NewMeshService extends NewService {
    @SerializedName("Connect")
    private Connect connect;

    /**
     * @return the connect
     */
    public Connect getConnect() {
        return connect;
    }

    /**
     * @param connect the connect to set
     */
    public void setConnect(Connect connect) {
        this.connect = connect;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString() {
        return "NewService{" + "id='" + getId() + '\'' + ", name='" + getName() + '\'' + ", tags=" + getTags()
                + ", address='" + getAddress() + '\'' + ", meta=" + getMeta() + ", port=" + getPort()
                + ", enableTagOverride=" + getEnableTagOverride() + ", check=" + getCheck() + ", checks=" + getChecks()
                + ", connect=" + getConnect() + '}';
    }

}
