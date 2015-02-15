package ScEventPlayer

/**
 * Created by steve on 2/8/15.
 */
trait PlayCommand[TModel <: Aggregate[TModel]] {
  protected def Validate(model: TModel)
  protected def GetEvent(id: AggregateId[TModel], version: Int) : PlayEvent[TModel]

  def ExecuteOn(model : TModel): PlayEvent[TModel] = {
    Validate(model)
    GetEvent(model.id, model.latestVersion + 1)
  }
}
