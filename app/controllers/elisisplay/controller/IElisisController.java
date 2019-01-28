package controllers.elisisplay.controller;

import controllers.elisisplay.secure.ApplicationSection;

/**
 * Interface for ViewController.
 */
public interface IElisisController {

    ApplicationSection mySection();

    void initObservator();
}
