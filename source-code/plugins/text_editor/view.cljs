(ns plugins.text-editor.view
  (:require
    ["react" :as react]
    [reagent.core :as r :refer [atom]]

    [x.app-core.api :as a]
    [x.app-elements.api :as elements]

    [jodit-react :default JoditEditor]))

;; -----------------------------------------------------------------------------
;; ---- Configurations ----

(def default-config {:language "hu"
                     :minHeight "400"
                     :cleanHTML true
                     :cleanWhitespace true})

;; ---- Configurations ----
;; -----------------------------------------------------------------------------

(defn memofn
  ([f]
   (react/useMemo f #js []))
  ([f deps]
   (react/useMemo f (to-array deps))))


(defn start-timer [watcher f & [ms]]
  (.clearTimeout js/window @watcher)
  (reset! watcher (.setTimeout js/window (fn []
                                           (f)
                                           (.clearTimeout js/window @watcher))
                                         (if ms ms 500))))

(defn on-type-ended [watcher f & [ms]]
  (start-timer watcher f (if ms ms 500)))

;; -----------------------------------------------------------------------------
;; ---- Components ----


(defn text-editor [{:keys [config value-path local-value editor-props] :as props}]
  (let [config  (memofn (fn [] (clj->js (if config config default-config))))
        watcher (atom nil)]
     [:> JoditEditor
       (merge {:config   config
               :value    local-value
               :onChange #(on-type-ended watcher
                            (fn []
                              (a/dispatch-sync [:db/set-item! value-path %])))}
              editor-props)]))

(defn view [{:keys [value-path]}]
  ;  @param {:value-path   (keywords in vector)
  ;          :config     (hash-map)(opt)                                        ;; optional config if not included default-config going to use
  ;          :editor-props (hash-map)(opt)}]                                    ;; optional props for JoditEditor in case have to overwrite something
  ;
  ; @usage [plugins/text-editor {:value-path [:my-value-path]})
  ;
  ; @return [reagent-component]}
  (assert value-path
          (str "Must provide :value-path in text-editor config"))
  (fn [config]
    (let [init-value (or @(a/subscribe [:db/get-item value-path]) "")
          editor-settings (assoc config :local-value init-value)]
       [:div
        [:f> text-editor editor-settings]])))
