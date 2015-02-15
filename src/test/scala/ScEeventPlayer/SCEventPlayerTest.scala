package ScEeventPlayer

import ScEventPlayer._
import org.scalatest.FunSuite

class EventTest extends FunSuite {

  val id: AggregateId[StubModel] = new AggregateId(1)

  test("Apply Event Changes to Aggregate"){
    val version = 1
    val model = new StubModel(id, 0)
    val actualEvt = new StubChangeEvent(id, version)
    actualEvt.ApplyTo(model)

    assert(model.boolVal === actualEvt.boolVal)
    assert(model.latestVersion === version)
  }
}

class CommandTest extends FunSuite {
  val id: AggregateId[StubModel] = new AggregateId(1)

  test("Execute Command"){
    val model = new StubModel(id)
    val command = new StubPostCommand(true);
    val actual = command.ExecuteOn(model)

    assert(actual.aggregateId === 1)
    assert(actual.version === 1)

    assert(actual.asInstanceOf[StubChangeEvent].boolVal === true)
  }

  test("Execute Command - Validation Throws Exception"){
    val model = new StubModel(id)
    val command = new StubPostCommand(false)
    val thrown = intercept[Exception]{
      command.ExecuteOn(model)
    }

    assert(thrown.getMessage === "Invalid Input")
  }
}

class PlayerTest extends FunSuite{

  val id: AggregateId[StubModel] = new AggregateId(1)

  test("Play Changes"){
    val model = new StubModel(id)
    val player = new Player[StubModel]
    
    player.PlayFor(model, new StubChangeEvent(id, 1));

    assert(model.boolVal === true)
    assert(model.latestVersion === 1)

    player.PlayFor(model, new StubChangeEvent(id, 1));

    assert(model.boolVal === true)
    assert(model.latestVersion === 1)
  }
}

class StubPostCommand(b: Boolean) extends PlayCommand[StubModel] {
  override protected def Validate(model: StubModel): Unit = {
    if(!b){
      throw new Exception("Invalid Input")
    }
  }

  override protected def GetEvent(id: AggregateId[StubModel], version: Int): PlayEvent[StubModel] = {
    new StubChangeEvent(id, version, b)
  }
}

class StubModel (id : AggregateId[StubModel], version: Int = 0) extends Aggregate[StubModel](id, version) {
  var boolVal = false
}

class StubChangeEvent(id : AggregateId[StubModel], version: Int, val boolVal: Boolean = true) extends PlayEvent[StubModel](id, version) {
  override def ApplyChangesTo(model: StubModel): Unit = {
    model.boolVal = boolVal
  }
}