package net.sf.drftpd.remotefile;
import org.jdom.Element;

public class XMLSerialize {
	/*
	public static void main(String args[]) throws Exception {
		LinkedRemoteFile dir = new LinkedRemoteFile(null, new FileRemoteFile("/home/mog/dc", new File("/home/mog/dc")));
		Document doc = new Document(serialize(dir));
		new XMLOutputter("    ", true).output(doc, System.out);
	}
	*/
	/**
	 * @deprecated call file.toXML() instead
	 */
	public static Element serialize(LinkedRemoteFile file) {
		return file.toXML();
	}
//	public static Element serialize(LinkedRemoteFile file) {
//		Element fileElement;
//		if(file.isDirectory()) {
//			fileElement = new Element("directory");
//		} else {
//			fileElement = new Element("file");
//		}
//		fileElement.setAttribute("name", file.getName());
//		
//		fileElement.addContent(new Element("user").setText(file.getOwner()));
//		fileElement.addContent(new Element("group").setText(file.getGroup()));
//		//if(file.isFile()) {
//			fileElement.addContent(new Element("size").setText(Long.toString(file.length())));
//		//} 
//		fileElement.addContent(new Element("lastModified").setText(Long.toString(file.lastModified())));
//		
//		if(file.isDirectory()) {
//			Element contents = new Element("contents");
//			for(Iterator i = file.iterateFiles() ; i.hasNext(); ) {
//				contents.addContent(serialize((LinkedRemoteFile)i.next()));
//			}
//			fileElement.addContent(contents);
//		} else {
//			String checksum = "";
//			checksum = Long.toHexString(file.getCheckSum());
//
//			fileElement.addContent(new Element("checksum").setText(checksum));
//		}
//		
//		Element slaves = new Element("slaves");
//		for(Iterator i = file.getSlaves().iterator(); i.hasNext(); ) {
//			RemoteSlave rslave = (RemoteSlave)i.next();
//			slaves.addContent(new Element("slave").setAttribute("name", rslave.getName()));
//		}
//		fileElement.addContent(slaves);
//		
//		return fileElement;
//	}
}
