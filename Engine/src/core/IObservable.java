package core;

public interface IObservable {
	
	void addObserver(IObserver obsever);
	void removeObserver(IObserver observer);
	
}
