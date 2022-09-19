
(ns app.vehicles.backend.viewer.mutations
    (:require [com.wsscode.pathom3.connect.operation :as pathom.co :refer [defmutation]]
              [mid-fruits.candy                      :refer [param return]]
              [mongo-db.api                          :as mongo-db]
              [pathom.api                            :as pathom]
              [x.server-user.api                     :as user]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defmutation delete-item!
             [_ {:keys [item-id]}]
             {::pathom.co/op-name 'vehicles.viewer/delete-item!}
             (mongo-db/remove-document! "vehicles" item-id))

(defmutation undo-delete-item!
             [_ {:keys [item]}]
             {::pathom.co/op-name 'vehicles.viewer/undo-delete-item!}
             (mongo-db/insert-document! "vehicles" item))

(defmutation duplicate-item!
             [{:keys [request] :as env} {:keys [item-id]}]
             {::pathom.co/op-name 'vehicles.viewer/duplicate-item!}
             (mongo-db/duplicate-document! "vehicles" item-id
                                           {:prototype-f #(user/duplicated-document-prototype request :vehicle %)}))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(def HANDLERS [delete-item! duplicate-item! undo-delete-item!])

(pathom/reg-handlers! ::handlers HANDLERS)
