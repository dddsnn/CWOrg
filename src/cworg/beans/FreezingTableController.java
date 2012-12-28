package cworg.beans;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean
public class FreezingTableController {

	@PostConstruct
	public void init() {
		FreezingTableBean ftb =
				(FreezingTableBean) FacesContext.getCurrentInstance()
						.getExternalContext().getSessionMap().get("backBean");
		Map<String, Object> asd =
				FacesContext.getCurrentInstance().getExternalContext()
						.getSessionMap();
		System.out.println(ftb);
	}
	// TODO
}
