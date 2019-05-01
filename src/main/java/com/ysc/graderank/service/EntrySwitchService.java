package com.ysc.graderank.service;

import com.ysc.graderank.mapper.EntrySwitchMapper;
import com.ysc.graderank.pojo.EntrySwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntrySwitchService {
    @Autowired
    private EntrySwitchMapper entrySwitchMapper;

    public EntrySwitch getOne() {
        List<EntrySwitch> entrySwitchList = entrySwitchMapper.selectAll();
        if (entrySwitchList.isEmpty()) {
            return null;
        }
        return entrySwitchList.get(0);
    }

    public void update(EntrySwitch entrySwitch) {
        entrySwitchMapper.updateByPrimaryKeySelective(entrySwitch);
    }
}
