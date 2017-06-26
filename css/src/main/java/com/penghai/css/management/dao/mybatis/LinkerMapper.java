package com.penghai.css.management.dao.mybatis;

import java.util.List;

import com.penghai.css.management.model.Linker;
import com.penghai.css.management.model.LinkerReport;

/**
 * Linker DAO Mapper
 * @author 刘立华
 * @Date 2017-05-10
 */
public interface LinkerMapper {
    
    /**
     * 查询linker
     * @param linkerId
     * @return Linker
     */
    Linker selectLinker(String linkerId);
    
    /**
     * 查询所有linker
     * @param 无
     * @return Linker List
     */
    List<Linker> selectAllLinkers();      
    
    /**
     * 更新linker
     * 返回值： 0：成功，1 失败
     * @param Linker
     * @return int
     */
    int updateLinker(Linker linker);  
    
    /**
     * 插入linker
     * 返回值： 0：成功，1 失败
     * @param Linker
     * @return int
     */
    int insertLinker(Linker linker);
    
    /**
     * 插入linkerReport
     * 返回值： 0：成功，1 失败
     * @param Linker
     * @return int
     */
    int insertLinkerReport(LinkerReport linkerReport);  
    /**
     * 查询Liner下的所有报告数据
     * @author 刘晓强
     * @date 2017年5月13日
     * @param linkerId
     * @return
     */
    List<LinkerReport> selectLinkerReportsbyLinkerId(String linkerId);
}