package com.SpringOS.system.entity.common;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 */
// JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity<E> {
	private Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuffer strbfr = new StringBuffer();
		Class cls = this.getClass();
		Field[] fld =  cls.getDeclaredFields();
		Method[] mds = cls.getMethods();
		Method md;
		strbfr.append("<"+cls+">\n");
		strbfr.append("id = "+this.getId()+"\n");
		for (int i = 0; i < fld.length; i++) {
			try {
				fld[i].setAccessible(true);
				strbfr.append(fld[i].getName()+" = "+fld[i].get(this)+"\n");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		strbfr.append("</"+cls+">\n");
		return strbfr.toString();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

//	public E clone()throws InstantiationException,IllegalAccessException {
//		StringBuffer strbfr = new StringBuffer();
//		Class cls = this.getClass();
//		E e = (E)cls.newInstance();
//		Field[] fld =  cls.getDeclaredFields();
//		Method[] mds = cls.getMethods();
//		Method md;
//		strbfr.append("<"+cls+">\n");
//		strbfr.append("id = "+this.getId()+"\n");
//		for (int i = 0; i < fld.length; i++) {
//			try {
//				fld[i].setAccessible(true);
//				strbfr.append(fld[i].getName()+" = "+fld[i].get(this)+"\n");
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			} catch (Exception e){
//				e.printStackTrace();
//			}
//		}
//		strbfr.append("</"+cls+">\n");
//		return strbfr.toString();
//	}

	public static String captureName(String name) {
//     	name = name.substring(0, 1).toUpperCase() + name.substring(1);
//     	return  name;
		char[] cs=name.toCharArray();
		cs[0]-=32;
		return String.valueOf(cs);

	}
}
