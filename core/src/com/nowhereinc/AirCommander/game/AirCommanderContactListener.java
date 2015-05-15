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
		
		// bullet hits plane or player block code
		
		String fixtureALong = fixtureA.getUserData().toString();
		String fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 6));
		
		String fixtureBLong = fixtureB.getUserData().toString();
		String fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 3));
	
		if (fixtureAShort.equals("bullet") &&
		    fixtureBShort.equals("pla")) {
			
			fixtureA.setUserData("delete");
			fixtureB.setUserData("delete");
			
		}
		
		fixtureALong = fixtureA.getUserData().toString();
		fixtureAShort = fixtureALong.substring(0, Math.min(fixtureALong.length(), 3));
		
		fixtureBLong = fixtureB.getUserData().toString();
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 6));
		
		if (fixtureAShort.equals("pla") &&
			fixtureBShort.equals("bullet")) {
			
			fixtureA.setUserData("delete");
			fixtureB.setUserData("delete");
		
		}

	}
	
	public void endContact(Contact contact) {
		
	}
	
	public void preSolve(Contact contact, Manifold manifold) {
	
	}
	
	public void postSolve(Contact contact, ContactImpulse contactimpulse) {
		
	}

}