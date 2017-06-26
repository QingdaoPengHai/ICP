package com.penghai.css.analysis.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="writeHosts")
public class MycatWriteHostList {

	private List<MycatWriteHost> writeHosts;

	@XmlElement(name = "writeHost")
	public List<MycatWriteHost> getWriteHosts() {
		return writeHosts;
	}

	public void setWriteHosts(List<MycatWriteHost> writeHosts) {
		this.writeHosts = writeHosts;
	}
}
