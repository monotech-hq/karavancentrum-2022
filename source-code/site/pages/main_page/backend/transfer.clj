
(ns site.pages.main-page.backend.transfer
  (:require
    [x.server-core.api :as a]))


(a/reg-transfer!
  ::transfer
  {:data-f (fn [request]
             "asdfasdf")
   :target-path [:valami]})
