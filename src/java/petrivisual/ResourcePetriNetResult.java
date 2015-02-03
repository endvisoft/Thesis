package petrivisual;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.processmining.framework.log.LogReader;
import org.processmining.framework.models.ModelGraph;
import org.processmining.framework.models.ModelGraphPanel;
import org.processmining.framework.models.petrinet.PetriNet;
import org.processmining.framework.models.petrinet.PetriNetHierarchy;
import org.processmining.mining.petrinetmining.PetriNetResult;

public class ResourcePetriNetResult extends PetriNetResult
{
	ResourcePetriNet resourcePetriNet;
	public ResourcePetriNetResult(LogReader log, PetriNet net) {
		super(log, net);		
	}
	
	public ResourcePetriNetResult(ResourcePetriNet net)
	{
		super(net.getPetriNet());
		resourcePetriNet = net;		
	}	
	
	@Override
	protected void newHierarchy() {
		hierarchy = new PetriNetHierarchy() {
			protected void selectionChanged(Object selectedObject) {
				if (netContainer == null) {
					return;
				}
				if (selectedObject instanceof ModelGraph) {
					ModelGraph pnet = (ModelGraph) selectedObject;
					ModelGraphPanel gp = resourcePetriNet.getGrappaVisualization();
					if (gp != null) {
						gp.addGrappaListener(adapter);
					}
					netContainer.setViewportView(gp);
					mainPanel.validate();
					mainPanel.repaint();
				} else {
					JPanel p = new JPanel();
					p.add(new JLabel("Cannot visualize selection"));
					netContainer.setViewportView(p);
					mainPanel.validate();
					mainPanel.repaint();
				}
			}
		};

	}
	
	@Override
	public JComponent getVisualization() {
		editLogRelationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editRelations(null);
			}
		});
		ResourceHierarchy resourceHierarchy = null;
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
				hierarchy.getTreeVisualization(), netContainer);

		split.setOneTouchExpandable(true);

		if (algorithm != null) {
			buttonsPanel.add(editLogRelationsButton);
			mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		}
		mainPanel.add(split, BorderLayout.CENTER);

		return mainPanel;
	}
}