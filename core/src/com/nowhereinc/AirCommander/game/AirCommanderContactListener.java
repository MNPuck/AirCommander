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
		
		// plane hits player block code
		
		fixtureAShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 5));
	
		if (fixtureAShort.equals("plane") &&
		    fixtureBLong.equals("player")) {
			
			fixtureA.setUserData("delete");
			fixtureB.setUserData("delete");
			
		}
		
		fixtureBShort = fixtureBLong.substring(0, Math.min(fixtureBLong.length(), 5));
		
		if (fixtureALong.equals("player") &&
			fixtureBShort.equals("plane")) {
			
			fixtureA.setUserData("delete");
			fixtureB.setUserData("delete");
		
		}

	}
	
	public void endContact(Contact contact) {
		
	}
	
	public void preSolve(Contact contact, Manifold manifold) {
		
		// ignore bullet on bullet collisions
		
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		String fixtureALong = fixtureA.getUserData().toString();
		String fixtureBLong = fixtureB.getUserData().toString();
		
		if (fixtureALong.equals("bullet") &&
		    fixtureBLong.equals("bullet")) {
		
			contact.setEnabled(false);

		}
	
	}
	
	public void postSolve(Contact contact, ContactImpulse contactimpulse) {
			
	}

}