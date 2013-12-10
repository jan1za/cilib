/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.sourceforge.cilib.clustering.entity;

import java.util.ArrayList;

import junit.framework.Assert;
import net.sourceforge.cilib.algorithm.initialisation.ClonedPopulationInitialisationStrategy;
import net.sourceforge.cilib.algorithm.initialisation.DataDependantPopulationInitialisationStrategy;
import net.sourceforge.cilib.clustering.DataClusteringPSO;
import net.sourceforge.cilib.clustering.SlidingWindow;
import net.sourceforge.cilib.controlparameter.ConstantControlParameter;
import net.sourceforge.cilib.controlparameter.ControlParameter;
import net.sourceforge.cilib.entity.Property;
import net.sourceforge.cilib.entity.initialisation.StandardCentroidInitialisationStrategy;
import net.sourceforge.cilib.measurement.generic.Iterations;
import net.sourceforge.cilib.problem.ClusteringProblem;
import net.sourceforge.cilib.problem.QuantisationErrorMinimisationProblem;
import net.sourceforge.cilib.problem.solution.MinimisationFitness;
import net.sourceforge.cilib.stoppingcondition.Maximum;
import net.sourceforge.cilib.stoppingcondition.MeasuredStoppingCondition;
import net.sourceforge.cilib.type.types.container.CentroidHolder;
import net.sourceforge.cilib.type.types.container.ClusterCentroid;

import org.junit.Test;

public class ClusterParticleTest {

    /**
     * Test of getPosition method, of class ClusterParticle.
     */
    @Test
    public void testGetCandidateSolution() {
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setPosition(holder);

        Assert.assertEquals(instance.getPosition(), holder);
    }

    /**
     * Test of setPosition method, of class ClusterParticle.
     */
    @Test
    public void testSetCandidateSolution_CentroidHolder() {
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setPosition(holder);

        Assert.assertEquals(instance.getPosition(), holder);
    }

    /**
     * Test of calculateFitness method, of class ClusterParticle.
     */
    @Test
    public void testCalculateFitness() {
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        instance.put(Property.FITNESS, new MinimisationFitness(12.0));
        instance.put(Property.BEST_FITNESS, new MinimisationFitness(12.0));
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4);
        centroid.setDataItemDistances(new double[]{2,2,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setPosition(holder);
        CentroidHolder clearHolder =  new CentroidHolder();
        ClusterCentroid clearCentroid = ClusterCentroid.of(0,0,0,0);
        clearHolder.add(clearCentroid);
        clearHolder.add(clearCentroid);
        clearHolder.add(clearCentroid);
        instance.put(Property.VELOCITY, clearHolder);

        instance.setNeighbourhoodBest(instance);
        instance.put(Property.BEST_POSITION, instance.getPosition());

        DataClusteringPSO pso = new DataClusteringPSO();
        QuantisationErrorMinimisationProblem problem = new QuantisationErrorMinimisationProblem();
        SlidingWindow window = new SlidingWindow();
        pso.setSourceURL("library/src/test/resources/datasets/iris2.arff");
        window.setWindowSize(3);
        pso.setWindow(window);
        problem.setDomain("R(-5.12:5.12)");

        pso.setOptimisationProblem(problem);
        pso.addStoppingCondition(new MeasuredStoppingCondition(new Iterations(), new Maximum(), 1));

        DataDependantPopulationInitialisationStrategy init = new DataDependantPopulationInitialisationStrategy();
        init.setDelegate(new ClonedPopulationInitialisationStrategy());
        init.setEntityNumber(1);
        init.setEntityType(new ClusterParticle());
        pso.setInitialisationStrategy(init);

        pso.performInitialisation();
//        Topology topology = new GBestTopology<ClusterParticle>();
//        topology.add(instance);
        pso.setTopology(fj.data.List.list(instance));
        pso.run();

        Assert.assertEquals(Math.round(instance.getFitness().getValue() * 1e10) / 1e10,  Math.round(3.1291362326128439920284548286519 * 1e10) / 1e10);
    }

    /**
     * Test of getBestFitness method, of class ClusterParticle.
     */
    @Test
    public void testGetBestFitness() {
        ClusterParticle instance = new ClusterParticle();
        instance.put(Property.BEST_FITNESS, new MinimisationFitness(6.0));

        Assert.assertEquals(instance.getBestFitness().getValue(), 6.0);
    }

    /**
     * Test of getDimension method, of class ClusterParticle.
     */
    @Test
    public void testGetDimension() {
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setPosition(holder);

        Assert.assertEquals(instance.getDimension(), 3);
    }

    /**
     * Test of getPosition method, of class ClusterParticle.
     */
    @Test
    public void testGetPosition() {
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setPosition(holder);

        Assert.assertEquals(instance.getPosition(), holder);
    }

    /**
     * Test of getBestPosition method, of class ClusterParticle.
     */
    @Test
    public void testGetBestPosition() {
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.put(Property.BEST_POSITION, holder);

        Assert.assertEquals(instance.getBestPosition(), holder);
    }

    /**
     * Test of getVelocity method, of class ClusterParticle.
     */
    @Test
    public void testGetVelocity() {
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.put(Property.VELOCITY, holder);

        Assert.assertEquals(instance.getVelocity(), holder);
    }

    /**
     * Test of getNeighbourhoodBest method, of class ClusterParticle.
     */
    @Test
    public void testGetNeighbourhoodBest() {
        ClusterParticle instance = new ClusterParticle();
        ClusterParticle neighbour = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        neighbour.setPosition(holder);
        instance.setNeighbourhoodBest(neighbour);

        Assert.assertEquals(instance.getNeighbourhoodBest().getPosition(), holder);
    }

    /**
     * Test of updatePosition method, of class ClusterParticle.
     */
    @Test
    public void testUpdatePosition() {
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setPosition(holder.getClone());
        instance.put(Property.VELOCITY, holder);
        instance.put(Property.BEST_POSITION, holder);
        instance.put(Property.FITNESS, new MinimisationFitness(6.0));
        instance.put(Property.BEST_FITNESS, new MinimisationFitness(6.0));
        instance.setNeighbourhoodBest(instance);
        instance.updatePosition(instance.getPositionProvider().get(instance));

        Assert.assertNotSame(instance.getPosition(), holder);
    }

    /**
     * Test of updateVelocity method, of class ClusterParticle.
     */
    @Test
    public void testUpdateVelocity() {
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setPosition(holder.getClone());
        instance.put(Property.VELOCITY, holder);
        instance.put(Property.BEST_POSITION, holder);
        instance.put(Property.FITNESS, new MinimisationFitness(6.0));
        instance.put(Property.BEST_FITNESS, new MinimisationFitness(6.0));
        instance.setNeighbourhoodBest(instance);
        instance.updateVelocity(instance.getVelocityProvider().get(instance));

        Assert.assertNotSame(instance.getVelocity(), holder);
    }

    /**
     * Test of initialise method, of class ClusterParticle.
     */
    @Test
    public void testInitialise() {
        ClusteringProblem problem = new QuantisationErrorMinimisationProblem();
        problem.setDomain("R(-5.12:5.12)");
        problem.setNumberOfClusters(2);
        problem.setDimension(5);

        ClusterParticle instance = new ClusterParticle();
        StandardCentroidInitialisationStrategy strategy = new StandardCentroidInitialisationStrategy();
        ArrayList bounds = new ArrayList();
        ControlParameter[] params = new ControlParameter[2];
        params[0] = ConstantControlParameter.of(1.0);
        params[1] = ConstantControlParameter.of(3.0);
        bounds.add(params);
        params = new ControlParameter[2];
        params[0] = ConstantControlParameter.of(0.5);
        params[1] = ConstantControlParameter.of(2.1);
        bounds.add(params);
        params = new ControlParameter[2];
        params[0] = ConstantControlParameter.of(1.0);
        params[1] = ConstantControlParameter.of(3.0);
        bounds.add(params);
        params = new ControlParameter[2];
        params[0] = ConstantControlParameter.of(0.5);
        params[1] = ConstantControlParameter.of(2.1);
        bounds.add(params);
        params = new ControlParameter[2];
        params[0] = ConstantControlParameter.of(0.5);
        params[1] = ConstantControlParameter.of(2.1);
        bounds.add(params);
        strategy.setBounds(bounds);

        instance.setCentroidInitialisationStrategy(strategy);
        instance.initialise(problem);

        Assert.assertEquals(instance.getDimension(), 2);
        Assert.assertTrue(((CentroidHolder) instance.getPosition()).get(0).size() == 5);
        Assert.assertEquals(instance.getBestPosition().size(), 2);
        Assert.assertTrue(((CentroidHolder) instance.getBestPosition()).get(0).size() == 5);
    }

    /**
     * Test of reinitialise method, of class ClusterParticle.
     */
    @Test
    public void testReinitialise() {
        ClusterParticle instance = new ClusterParticle();

        ClusteringProblem problem = new QuantisationErrorMinimisationProblem();
        problem.setDomain("R(-5.12:5.12)");
        problem.setNumberOfClusters(2);
        problem.setDimension(5);

        StandardCentroidInitialisationStrategy strategy = new StandardCentroidInitialisationStrategy();
        ArrayList bounds = new ArrayList();
        ControlParameter[] params = new ControlParameter[2];
        params[0] = ConstantControlParameter.of(1.0);
        params[1] = ConstantControlParameter.of(3.0);
        bounds.add(params);
        params = new ControlParameter[2];
        params[0] = ConstantControlParameter.of(0.5);
        params[1] = ConstantControlParameter.of(2.1);
        bounds.add(params);
        params = new ControlParameter[2];
        params[0] = ConstantControlParameter.of(1.0);
        params[1] = ConstantControlParameter.of(3.0);
        bounds.add(params);
        params = new ControlParameter[2];
        params[0] = ConstantControlParameter.of(0.5);
        params[1] = ConstantControlParameter.of(2.1);
        bounds.add(params);
        params = new ControlParameter[2];
        params[0] = ConstantControlParameter.of(0.5);
        params[1] = ConstantControlParameter.of(2.1);
        bounds.add(params);
        strategy.setBounds(bounds);

        instance.setCentroidInitialisationStrategy(strategy);

        instance.initialise(problem);

        CentroidHolder holder = (CentroidHolder) instance.getPosition().getClone();

        instance.reinitialise();

        Assert.assertNotSame(holder, (CentroidHolder) instance.getPosition());
    }

    /**
     * Test of setNeighbourhoodBest method, of class ClusterParticle.
     */
    @Test
    public void testSetNeighbourhoodBest() {
        ClusterParticle instance = new ClusterParticle();
        ClusterParticle neighbourhoodBest = new ClusterParticle();

        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);

        neighbourhoodBest.setPosition(holder);
        neighbourhoodBest.put(Property.VELOCITY, holder);
        neighbourhoodBest.put(Property.BEST_POSITION, holder);

        instance.setNeighbourhoodBest(neighbourhoodBest);

        Assert.assertEquals(instance.getNeighbourhoodBest(), neighbourhoodBest);
    }


}
