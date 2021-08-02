package com.zhang.seasons.service;

import com.zhang.seasons.bean.Work;
import com.zhang.seasons.mapper.WorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService {
    private static final String[] SORT_SEQ = new String[] {"created", "price", "laud_num"};
    private static final String[] SORT_TYPE = new String[] {" asc", " desc"};

    @Autowired
    private WorkMapper workMapper;

    public boolean insertWork(Work work) {
        return workMapper.insert(work) == 1;
    }

    public boolean deleteWork(int wid) {
        return workMapper.delete(wid) == 1;
    }

    public boolean updateWork(Work work) {
        return workMapper.update(work) == 1;
    }

    public boolean updateWorkLaud(int wid, int addition) {
        return workMapper.updateLaud(wid, addition) == 1;
    }

    public boolean updateWorkState(int wid, int state) {
        return workMapper.updateState(wid, state) == 1;
    }

    public Work selectWork(int wid) {
        return workMapper.select(wid);
    }

    public List<Work> selectWorkByTitle(String title, int[] sort) {
        return workMapper.selectByTitle(title, SORT_SEQ[sort[0]] + SORT_TYPE[sort[1]]);
    }

    public List<Work> selectWorkByStyle(String style, int[] sort) {
        return workMapper.selectByStyle(style, SORT_SEQ[sort[0]] + SORT_TYPE[sort[1]]);
    }

    public List<Work> selectWorkByUid(int uid, int[] sort) {
        return workMapper.selectByUid(uid, SORT_SEQ[sort[0]] + SORT_TYPE[sort[1]]);
    }

    public List<Work> selectAllWork(int[] sort) {
        return workMapper.selectAll(SORT_SEQ[sort[0]] + SORT_TYPE[sort[1]]);
    }

    public List<Work> selectDisapproveWork(int[] sort) {
        return workMapper.selectDisapprove(SORT_SEQ[sort[0]] + SORT_TYPE[sort[1]]);
    }

    public List<Work> selectAllWorkByAdmin(int[] sort) {
        return workMapper.selectAllByAdmin(SORT_SEQ[sort[0]] + SORT_TYPE[sort[1]]);
    }
}
