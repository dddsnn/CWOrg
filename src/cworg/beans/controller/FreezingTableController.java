package cworg.beans.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import cworg.beans.model.FreezingTableModel;
import cworg.data.Tank;

@ManagedBean
public class FreezingTableController {
	@ManagedProperty(value = "#{freezingTableModel}")
	private FreezingTableModel model;

	public FreezingTableController() {
	}

	public FreezingTableModel getModel() {
		return model;
	}

	public void setModel(FreezingTableModel model) {
		this.model = model;
	}

	public String asd() {
		List<Tank> dpt = model.getDisplayedTanks();
		Tank t = new Tank();
		t.setShortName("TankName2");
		dpt.add(t);
		model.setDisplayedTanks(dpt);
		return null;
	}
}
