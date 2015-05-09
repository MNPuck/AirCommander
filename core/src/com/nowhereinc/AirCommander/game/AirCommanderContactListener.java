package com.nowhereinc.AirCommander.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;




public class AirCommanderContactListener implements ContactListener {
	
	public static final String TAG = Level.class.getName();
	
	
	public void beginContact(Contact contact) {
	
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		// ball hits block code
		
		String fixtureALong = fixtureA.getUserData().toString();
		String fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 4));
		
		String fixtureBLong = fixtureB.getUserData().toString();
		String fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 5));
	
		if (fixtureAShort.equals("ball") &&
		    fixtureBShort.equals("block")) {
			
			fixtureB.setUserData("delete");
			
		}
		
		fixtureALong = fixtureA.getUserData().toString();
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 5));
		
		fixtureBLong = fixtureB.getUserData().toString();
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 4));
		
		if (fixtureAShort.equals("block") &&
			fixtureBShort.equals("ball")) {
			
			fixtureA.setUserData("delete");
		
		}

	}
	
	public void endContact(Contact contact) {
		
	}
	
	public void preSolve(Contact contact, Manifold manifold) {
	
	}
	
	public void postSolve(Contact contact, ContactImpulse contactimpulse) {
		
	}

}