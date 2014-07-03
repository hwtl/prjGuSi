/**
 * 
 */
package com.gusi.boms.service;

import com.gusi.BaseTest;
import org.junit.Test;

import com.dooioo.plus.oms.dyEnums.EmployeeStatus;

/**
 * @author Alex Yu
 * @Created 2013年11月29日下午4:57:58
 */
public class TestOMSExt extends BaseTest {

	@Test
	public void testEmployeeStatus() {
		EmployeeStatus employeeStatus = EmployeeStatus.UnFormal;
		int kk = employeeStatus.ordinal();
		System.out.println(employeeStatus.toString() + ": " + kk);
		
		employeeStatus = EmployeeStatus.Formal;
		kk = employeeStatus.ordinal();
		System.out.println(employeeStatus.toString() + ": " + kk);
		
		employeeStatus = EmployeeStatus.Leaving;
		kk = employeeStatus.ordinal();
		System.out.println(employeeStatus.toString() + ": " + kk);
		
		employeeStatus = EmployeeStatus.Leaved;
		kk = employeeStatus.ordinal();
		System.out.println(employeeStatus.toString() + ": " + kk);
		
		employeeStatus = EmployeeStatus.ToRegisteHeadquarters;
		kk = employeeStatus.ordinal();
		System.out.println(employeeStatus.toString() + ": " + kk);
		
		employeeStatus = EmployeeStatus.ToRegisteBranch;
		kk = employeeStatus.ordinal();
		System.out.println(employeeStatus.toString() + ": " + kk);
		
		employeeStatus = EmployeeStatus.UnRegistedHeadquarters;
		kk = employeeStatus.ordinal();
		System.out.println(employeeStatus.toString() + ": " + kk);
		
		employeeStatus = EmployeeStatus.UnRegistedBranch;
		kk = employeeStatus.ordinal();
		System.out.println(employeeStatus.toString() + ": " + kk);
	}
}
