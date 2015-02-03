/***********************************************************
 *      This software is part of the ProM package          *
 *             http://www.processmining.org/               *
 *                                                         *
 *            Copyright (c) 2003-2006 TU/e Eindhoven       *
 *                and is licensed under the                *
 *            Common Public License, Version 1.0           *
 *        by Eindhoven University of Technology            *
 *           Department of Information Systems             *
 *                 http://is.tm.tue.nl                     *
 *                                                         *
 **********************************************************/

package org.processmining.framework.models.petrinet.algorithms;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilderFactory;

import org.processmining.framework.log.LogEvent;
import org.processmining.framework.models.petrinet.PetriNet;
import org.processmining.framework.models.petrinet.Place;
import org.processmining.framework.models.petrinet.Token;
import org.processmining.framework.models.petrinet.Transition;
import org.processmining.framework.models.petrinet.TransitionCluster;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Reads a PetriNet from a PNML file.
 * <p>
 * The PNML file that is read should conform at least to the basicPNML schema
 * (see http://www.informatik.hu-berlin.de/top/pnml/basicPNML.rng), although
 * this is not checked.
 * <p>
 * In addition to this schema, the following tags are supported:
 * <ul>
 * <li><b>logevent</b> (inside the <b>toolspecific</b> tag of the transition
 * tag): this tag has a name and a type tag as its children. For example,
 * 
 * <pre>
 *        &lt;logevent&gt;
 *           &lt;name&gt;TASK_1&lt;/name&gt;
 *           &lt;type&gt;complete&lt;/type&gt;
 *        &lt;/logevent&gt;
 * </pre>
 * 
 * <li><b>transitionlabel</b> (inside the <b>toolspecific</b> tag of the
 * transition tag): this tag is only read if there is no logevent tag
 * <li><b>name</b> (inside a transition tag): this tag is used in the Petrinet
 * Kernel PNML files. If this tag is present, only the text in the sub-element
 * &lt;value&gt; is used. For example,
 * 
 * <pre>
 *        &lt;name&gt;
 *           &lt;graphics&gt;...&lt;/graphics&gt;
 *           &lt;value&gt;TASK_1 (complete)&lt;/value&gt;
 *        &lt;/name&gt;
 * </pre>
 * 
 * </ul>
 * 
 * All values for the type attribute of a net tag are accepted. This type
 * attribute is also NOT used to decide whether to use the logevent or name
 * tags. These tags will ALWAYS be used when they are present in the PNML file.
 * <p>
 * Any unknown tags are simply ignored without warning.
 * 
 * @author Peter van den Brand
 * @version 1.0
 */

public class PnmlReader {

	public PnmlReader() {
	}

	private HashMap places;
	private HashMap transitions;

	public PetriNet read(InputStream input) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		Document doc;
		PetriNet result = new PetriNet();
		NodeList netNodes;

		dbf.setValidating(false);
		dbf.setIgnoringComments(true);
		dbf.setIgnoringElementContentWhitespace(true);

		doc = dbf.newDocumentBuilder().parse(input);

		// check if root element is a <pnml> tag
		if (!doc.getDocumentElement().getTagName().equals("pnml")) {
			throw new Exception("pnml tag not found");
		}

		netNodes = doc.getDocumentElement().getElementsByTagName("net");
		if (netNodes.getLength() > 0) {
			parseNet(netNodes.item(0), result);
		}
		return result;
	}

	public PetriNet read(Node node) throws Exception {
		PetriNet result = new PetriNet();
		parseNet(node, result);
		return result;
	}

	private void parseNet(Node node, PetriNet net) throws Exception {
		Node id = node.getAttributes().getNamedItem("id");
		Node type = node.getAttributes().getNamedItem("type");

		// check id and type
		if (id == null || id.getNodeValue() == null) {
			throw new Exception("net tag is missing the id attribute");
		}
		if (type == null || type.getNodeValue() == null) {
			throw new Exception("net tag is missing the type attribute)");
		}

		places = new HashMap();
		transitions = new HashMap();

		parsePlaces(node, net);
		parseTransitions(node, net);
		parseArcs(node, net);

		NodeList children = node.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);

			if (n.getNodeName().equals("toolspecific")
					&& n.getAttributes().getNamedItem("tool").getNodeValue()
							.equals("ProM")) {
				foundToolSpecific();
				parseClusters(n, net);
			}
		}
	}

	/**
	 * parseClusters
	 * 
	 */
	private void parseClusters(Node node, PetriNet net) {

		NodeList children = node.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);

			if (n.getNodeName().equals("cluster")) {
				String name = n.getAttributes().getNamedItem("name")
						.getNodeValue();
				TransitionCluster tc = new TransitionCluster(name);
				NodeList trans = n.getChildNodes();
				for (int j = 0; j < trans.getLength(); j++) {
					if (trans.item(j).getNodeName().equals("trans")) {
						String transName = trans.item(j).getFirstChild()
								.getNodeValue();
						Transition t = (Transition) transitions.get(transName);
						tc.add(t);
					}
				}
				net.addCluster(tc);
			}
		}

		/*
		 * if ((net.getClusters()!=null) && (net.getClusters().size()>0)) { it =
		 * net.getClusters().iterator();
		 * bw.write("  <toolspecific tool=\"ProM\" version=\"" + About.VERSION +
		 * "\">\n"); while (it.hasNext()) { TransitionCluster tc =
		 * (TransitionCluster)it.next();
		 * bw.write("    <cluster name=\""+tc.getLabel()+"\">\n"); Iterator it2
		 * = tc.iterator(); while (it2.hasNext()) { Transition t = (Transition)
		 * it2.next();
		 * bw.write("      <trans>trans_"+t.getNumber()+"</trans>\n"); }
		 * bw.write("    </cluster>\n"); } bw.write("  </toolspecific>\n"); }
		 * return null; }
		 */
	}

	private void parsePlaces(Node node, PetriNet net) throws Exception {
		NodeList children = node.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);

			if (n.getNodeName().equals("place")) {
				String name = "";
				int noTok = 0;

				for (int j = 0; j < n.getChildNodes().getLength(); j++) {
					Node n2 = n.getChildNodes().item(j);

					if (n2.getNodeName().equals("name")) {
						NodeList nameChildren = n2.getChildNodes();

						for (int k = 0; k < nameChildren.getLength(); k++) {
							Node gn = nameChildren.item(k);

							if (gn.getNodeName().equals("text")
									&& gn.hasChildNodes()) {
								name = gn.getFirstChild().getNodeValue();
							}
						}
					}
					if (n2.getNodeName().equals("initialMarking")) {
						NodeList nameChildren = n2.getChildNodes();

						for (int k = 0; k < nameChildren.getLength(); k++) {
							Node gn = nameChildren.item(k);

							if ((gn.getNodeName().equals("text") || gn
									.getNodeName().equals("value"))
									&& gn.hasChildNodes()) {
								noTok = Integer.parseInt(gn.getFirstChild()
										.getNodeValue());
							}
						}
					}
				}

				Node id = n.getAttributes().getNamedItem("id");
				Place p;

				if (id == null || id.getNodeValue() == null) {
					throw new Exception(
							"place tag is missing the id attribute)");
				}
				p = new Place(id.getNodeValue(), net);
				net.addPlace(p);
				places.put(id.getNodeValue(), p);
				if (!name.equals("")) {
					p.setIdentifier(name);
				}
				for (int j = 0; j < noTok; j++) {
					p.addToken(new Token());
				}
			}
		}
	}

	private void parseTransitions(Node node, PetriNet net) throws Exception {
		NodeList children = node.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);

			if (n.getNodeName().equals("transition")) {
				Node id = n.getAttributes().getNamedItem("id");
				Transition t;

				if (id == null || id.getNodeValue() == null) {
					throw new Exception(
							"transition tag is missing the id attribute)");
				}
				t = parseTrans(n, net);
				net.addTransition(t);
				transitions.put(id.getNodeValue(), t);
			}
		}
	}

	private LogEvent parseLogEvent(Node node) throws Exception {
		NodeList children = node.getChildNodes();
		String logeventName = null;
		String logeventType = null;
		String name = null;

		for (int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);
			if (n.getNodeName().equals("logevent")) {
				NodeList logeventChildren = n.getChildNodes();

				for (int j = 0; j < logeventChildren.getLength(); j++) {
					Node gn = logeventChildren.item(j);

					if (gn.getNodeName().equals("name") && gn.hasChildNodes()) {
						logeventName = gn.getFirstChild().getNodeValue();
					}
					if (gn.getNodeName().equals("type") && gn.hasChildNodes()) {
						logeventType = gn.getFirstChild().getNodeValue();
					}
				}
			}
		}
		if (logeventName != null && logeventType != null) {
			return new LogEvent(logeventName, logeventType);
		} else {
			return null;
		}
	}

	private Transition parseTrans(Node node, PetriNet net) throws Exception {
		NodeList children = node.getChildNodes();
		LogEvent e = null;
		String name = null;
		String valueName = null;

		for (int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);

			if (n.getNodeName().equals("name")) {
				NodeList nameChildren = n.getChildNodes();

				for (int j = 0; j < nameChildren.getLength(); j++) {
					Node gn = nameChildren.item(j);

					if ((gn.getNodeName().equals("text") || gn.getNodeName()
							.equals("value"))
							&& gn.hasChildNodes()) {
						name = gn.getFirstChild().getNodeValue();
					}

					if (gn.getNodeName().equals("value") && gn.hasChildNodes()) {
						valueName = gn.getFirstChild().getNodeValue();
					}
				}
			}

			if (n.getNodeName().equals("toolspecific")
					&& n.getAttributes().getNamedItem("tool").getNodeValue()
							.equals("ProM")) {
				foundToolSpecific();
				e = parseLogEvent(n);
			}
			if(n.getNodeName().equals("link"))
			{
				NodeList wsdl = n.getChildNodes();
				Node loc = wsdl.item(0);
				if(loc.getNodeName().equals("location"))
				{
					String location = loc.getFirstChild().getNodeValue();
				}
			}
			if ((valueName != null) && (!valueName.equals(""))) {
				e = new LogEvent(name, "auto");
			}
		}

		if (e != null) {
			// we have enough info for a LogEvent
			Transition t = new Transition(e, net);
			if (name != null) {
				t.setIdentifier(name);
			}
			return t;
		} else if (name != null) {
			return new Transition(name, net);
		} else {
			// use id attribute of transition tag
			return new Transition(node.getAttributes().getNamedItem("id")
					.getNodeValue(), net);
		}
	}

	private void parseArcs(Node node, PetriNet net) throws Exception {
		NodeList children = node.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);

			if (n.getNodeName().equals("arc")) {
				Node id = n.getAttributes().getNamedItem("id");
				Node source = n.getAttributes().getNamedItem("source");
				Node target = n.getAttributes().getNamedItem("target");

				if (id == null || id.getNodeValue() == null || source == null
						|| source.getNodeValue() == null || target == null
						|| target.getNodeValue() == null) {
					throw new Exception(
							"arc tag is missing id, source or target attribute)");
				}

				if (places.get(source.getNodeValue()) != null) {
					// from place to transition
					net
							.addEdge((Place) places.get(source.getNodeValue()),
									(Transition) transitions.get(target
											.getNodeValue()));
				} else {
					// from transition to place
					net.addEdge((Transition) transitions.get(source
							.getNodeValue()), (Place) places.get(target
							.getNodeValue()));
				}
			}
		}
	}

	protected void foundToolSpecific() {
	}

}
