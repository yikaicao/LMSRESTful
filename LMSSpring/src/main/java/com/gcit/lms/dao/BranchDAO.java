package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Branch;

public class BranchDAO extends BaseDAO implements ResultSetExtractor<List<Branch>> {

	public List<Branch> readAllBranches() throws DataAccessException {
		return template.query("select * from tbl_library_branch", this);
	}

	/**
	 * Note: only extract first level of data from tbl_library_branch.
	 */
	@Override
	public List<Branch> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Branch> branches = new ArrayList<>();
		while (rs.next()) {
			Branch br = new Branch();
			br.setBranchId(rs.getInt("branchId"));
			br.setBranchName(rs.getString("branchName"));
			br.setBranchAddress(rs.getString("branchAddress"
					+ ""));
			branches.add(br);
		}
		return branches;
	}

}
