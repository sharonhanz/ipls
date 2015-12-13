package util;

public class RDFHelper {
	private String content = "";
	public int initRDF(){
		content = content + "<rdf:RDF\n\txmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n\txmlns:paper=\"http://202.120.40.175:40011/Entity/U6967b4d16323d2/\" >\n";
		return 0;
	}
	public String getRDF(){
		return content;
	}
	public int setDescription(String uri){
		content = content + "<rdf:Description rdf:about=\"" + uri + "\">\n";
		return 0;
	}
	public int setTarget(String tag, String value){
		content = content + "\t<paper:" + tag + ">" + value + "</paper:" + tag + ">\n";
		return 0;
	}
	public int endRDF(){
		content += "</rdf:Description>\n</rdf:RDF>";
		return 0;
	}
}
