/**
 * 
 */
package com.gusi.empTransfer.callback;

import com.gusi.empTransfer.model.EmployeeTransferTaskStatus;

/**
 * @author Alex Yu
 * @Created 2014年3月27日上午10:49:09
 */
public interface CallBackInterface<T> {

	public T callBack(EmployeeTransferTaskStatus empTransfer, Integer userCode, String opinion);
}
