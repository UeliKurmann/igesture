package org.ximtec.igesture.core;

public interface Visitor {

	void visit(GestureClass gestureClass);

	void visit(GestureSet gestureSet);

	void visit(GestureSample gestureSample);

	void visit(TestSet testSet);

	void visit(SampleDescriptor sampleDescriptor);

	void visit(DataObject dataObject);
}
