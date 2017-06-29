/*
 * Copyright 2017 Vadim Agishev (vadim.agishev@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.chicker.infobyipextractor.env

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import cats.data.Reader
import ru.chicker.infobyipextractor.infoprovider.{InfoByIpFreeGeoIpProvider, InfoByIpIp2IpProvider, InfoByIpProvider}
import ru.chicker.infobyipextractor.util.{HttpWeb, HttpWebImpl}

import scala.concurrent.ExecutionContext

trait Production extends Env {


  override implicit val executionContext: ExecutionContext = scala.concurrent.ExecutionContext.global

  override def httpWeb: Reader[Env, HttpWeb] = Reader { env =>
    new HttpWebImpl(env.actorSystem, env.materializer)
  }

  override def freeGeoIpProviderH: Reader[HttpWeb, InfoByIpProvider] = Reader { h =>
    new InfoByIpFreeGeoIpProvider(h)
  }

  override def ip2IpProviderH: Reader[HttpWeb, InfoByIpProvider] = Reader { h =>
    new InfoByIpIp2IpProvider(h)
  }

  override def actorSystem: ActorSystem = ActorSystem()

  override def materializer: ActorMaterializer = ActorMaterializer()(actorSystem)
}