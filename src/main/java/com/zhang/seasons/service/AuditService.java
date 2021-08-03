package com.zhang.seasons.service;

import com.zhang.seasons.bean.Audit;
import com.zhang.seasons.mapper.AuditMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AuditService {
    @Autowired
    private AuditMapper auditMapper;

    public boolean insertAudit(Audit audit) {
        return auditMapper.insert(audit) == 1;
    }

    public boolean deleteAudit(String item, int auditor) {
        return auditMapper.delete(item, auditor) == 1;
    }

    public boolean modifyAudit(Audit audit) {
        auditMapper.delete(audit.getItem(), audit.getAuditor());
        return auditMapper.insert(audit) == 1;
    }

    public List<Audit> selectAuditByAuditor(int auditor) {
        return auditMapper.selectByAuditor(auditor);
    }

    public List<Audit> selectAuditByTime(Timestamp start, Timestamp end) {
        return auditMapper.selectByTime(start, end);
    }
}
