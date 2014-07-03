package com.gusi.boms.model;

import com.dooioo.web.model.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.model
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-06-03 09:21)
 * Description: To change this template use File | Settings | File Templates.
 */
public class VEmployeeDetails extends BaseEntity {
    private static final long serialVersionUID = 6766290675291941661L;
    private VEmployeePosition [] positions;
    private EmployeeContractRecords [] contracts;
    private String delId;
    public VEmployeePosition[] getPositions() {
        return positions;
    }

    public void setPositions(VEmployeePosition[] positions) {
        this.positions = positions;
    }

    public EmployeeContractRecords[] getContracts() {
        return contracts;
    }

    public void setContracts(EmployeeContractRecords[] contracts) {
        this.contracts = contracts;
    }

    public String getDelId() {
        return delId;
    }

    public void setDelId(String delId) {
        this.delId = delId;
    }
}
